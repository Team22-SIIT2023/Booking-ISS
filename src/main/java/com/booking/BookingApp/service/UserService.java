package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.repository.*;
import com.booking.BookingApp.service.interfaces.IUserService;
import com.booking.BookingApp.utils.ImageUploadUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import javax.xml.stream.events.Comment;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class UserService implements IUserService {

    @Value("${image-path-users}")
    private String imagesDirPath;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    HostRepository hostRepository;

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    CommentsRepository commentsRepository;
    @Override
    public Collection<User> findAll() {
        return null;
    }

    @Override
    public User findOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<User> findByStatus(Status userStatus) {
        return userRepository.findByAccount_Status(userStatus);
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByAccount_Username(username);
    }
  
    @Override
    public boolean activateUser(String activationLink, String username){
        User user = userRepository.findByAccount_Username(username);
        System.out.println(username);
        if (!user.getActivationLink().equals(activationLink)) {
            return false;
        }
//        LocalDate now = LocalDate.now();
//
//        if (user.getActivationLinkDate().plusDays(1).isAfter(now)) {
//            return false;
//        }


        if(user != null) {
            user.getAccount().setStatus(Status.ACTIVE);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User update(User updatedUser) throws Exception {
        System.out.println(updatedUser);
        Long userId = updatedUser.getId();
        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser == null) {
            throw new Exception("User not found with ID: " + userId);
        }

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setAccount(updatedUser.getAccount());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        System.out.println(existingUser);

        return userRepository.save(existingUser);
    }


    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user.getAccount().getRoles().get(0).getName().equals("ROLE_GUEST")) {
            deleteGuest(user);
        } else if (user.getAccount().getRoles().get(0).getName().equals("ROLE_HOST")) {
            deleteHost(user);
        }
    }

    @Override
    public void deleteGuest(User user) {
        Guest guest = new Guest(user.getId(), user.getFirstName(), user.getLastName(), user.getAddress(),
                user.getPhoneNumber(), user.getAccount(), user.getDeleted(),
                userRepository.findFavoriteAccommodationsByGuestId(user.getId()));
        Collection<Request> reservations = requestRepository.findActiveReservationsForGuest(LocalDate.now(), guest);
        if(reservations.isEmpty()) {
            Collection<Comments> comments = commentsRepository.findByGuest_Id(user.getId());
            if(!comments.isEmpty()){
                for(Comments c: comments){
                    commentsRepository.deleteById(c.getId());
                }
            }
            userRepository.deleteById(user.getId());
        }
    }

    @Override
    public void deleteHost(User user) {
        Host host = new Host(user.getId(), user.getFirstName(), user.getLastName(),
                user.getAddress(), user.getPhoneNumber(), user.getAccount(), user.getDeleted());
        Collection<Request> reservations = requestRepository.findActiveReservationsForHost(LocalDate.now(), host);
        if (reservations.isEmpty()) {
            Collection<Accommodation> accommodations = accommodationRepository.findAllByHost(host);
            if(!accommodations.isEmpty()){
                for(Accommodation a: accommodations){
                    accommodationRepository.deleteById(a.getId());
                }
            }
        }
    }

    @Override
    public Collection<Accommodation> findFavorites(Long id) {
        return userRepository.findFavoriteAccommodationsByGuestId(id);
    }

//    @Override
//    public User save(User userRequest) {
//        User u = new User();
//        u.setUsername(userRequest.getUsername());
//
//        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
//        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
//        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//
//        u.setFirstName(userRequest.getFirstName());
//        u.setLastName(userRequest.getLastName());
//        u.setPhoneNumber(userRequest.getPhoneNumber());
//        u.setAddress(userRequest.getAddress());
//        u.setAccount(userRequest.getAccount());
//        u.setPicturePath(userRequest.getPicturePath());
//
//        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
//        List<Role> roles = roleService.findByName(userRequest.getAccount().getRoles().get(0).getName());
//        u.getAccount().setRoles(roles);
//
//        this.userRepository.save(u);
//        userRepository.flush();
//        return u;
//    }

    public void uploadImage(Long id, MultipartFile image) throws IOException {
        User user = findOne(id);

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String uploadDir = StringUtils.cleanPath(imagesDirPath + user.getId());

        System.out.println(uploadDir);

        ImageUploadUtil.saveImage(uploadDir, fileName, image);

        user.setImage(fileName);
        userRepository.save(user);
    }

    public List<String> getImages(Long id) throws IOException {
        System.out.println("GET SLIKE ID:    " + id);
        User user = findOne(id);
        List<String> base64Images = new ArrayList<>();

        String directoryPath = StringUtils.cleanPath(imagesDirPath + user.getId());
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] imageFiles = directory.listFiles();

            if (imageFiles != null) {
                for (File imageFile : imageFiles) {
                    if (imageFile.isFile()) {
                        byte[] imageData = Files.readAllBytes(imageFile.toPath());
                        String base64Image = Base64.getEncoder().encodeToString(imageData);
                        base64Images.add(base64Image);
                    }
                }
            }
        }
        return base64Images;
    }

    @Override
    public Collection<User> findAllByStatus(Status status) {
        System.out.println("uslo u status");
        return userRepository.findAllByAccountStatus(status);
    }

    @Override
    public User saveGuest(User userRequest) {
        Guest u = new Guest();
        u.setUsername(userRequest.getUsername());

        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        u.setFirstName(userRequest.getFirstName());
        u.setLastName(userRequest.getLastName());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        u.setAddress(userRequest.getAddress());
        u.setAccount(userRequest.getAccount());

        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        List<Role> roles = roleService.findByName(userRequest.getAccount().getRoles().get(0).getName());
        u.getAccount().setRoles(roles);
        this.userRepository.save(u);
//        userRepository.flush();
        return u;
    }

    @Override
    public User saveHost(User userRequest) {
        Host u = new Host();
        u.setUsername(userRequest.getUsername());

        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
        u.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        u.setFirstName(userRequest.getFirstName());
        u.setLastName(userRequest.getLastName());
        u.setPhoneNumber(userRequest.getPhoneNumber());
        u.setAddress(userRequest.getAddress());
        u.setAccount(userRequest.getAccount());
        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        List<Role> roles = roleService.findByName(userRequest.getAccount().getRoles().get(0).getName());
        u.getAccount().setRoles(roles);

        this.userRepository.save(u);
        userRepository.flush();
        return u;
    }

    @Override
    public User updateActivationLink(String activationLink, String username) {
        System.out.println("IDDDDDDDDDDDD" + username);
        User user1 = userRepository.findByAccount_Username(username);
        System.out.println(user1);

        user1.setActivationLink(activationLink);

        LocalDate activationTime = LocalDate.now();
        user1.setActivationLinkDate(activationTime);

        return userRepository.save(user1);
    }


}

