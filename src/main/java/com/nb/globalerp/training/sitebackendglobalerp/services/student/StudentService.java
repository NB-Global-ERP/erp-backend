package com.nb.globalerp.training.sitebackendglobalerp.services.student;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService {
    @Autowired
    public UserService(StageService stageService, UserRepository achievementRepository) {
        this.stageService = stageService;
        this.userRepository = achievementRepository;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Transactional
    public UserInfo getUserInfo() {
        log.debug("getUserInfo start");
        val userEmail = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        log.debug("getUserInfo get user email: " + userEmail);
        return userInfoFromData(userRepository.findByStudentDataEmail(userEmail).get());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Transactional
    public Integer getUserPoints() {
        val userEmail = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.findByStudentDataEmail(userEmail).get().getScore();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Transactional
    public UserInfo editUserInfo(UserEditableInfo info) {
        val userEmail = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return setUserInfo(userRepository.findByStudentDataEmail(userEmail).get(), info);
    }

    public List<RatingItem> getUsersRating() {
        return getUsersRating(SortingType.DESC);
    }

    @Transactional
    public List<RatingItem> getUsersRating(SortingType sortingType) {
        return userRepository.findAllAlive(
                        Sort.by(sortingType.toDirection(), "score.score")
                                .and(Sort.by(Sort.Direction.ASC, "name"))
                ).stream()
                .filter(user -> {
                    val stage = stageService.getCurrentStage();
                    if (stage.getStage().equals(Stage.TEAMS_JOINING)) {
                        return user.getPermissions().contains(UserPermission.TEAM_JOIN);
                    } else {
                        return user.getPermissions().contains(UserPermission.TASKS_DO);
                    }
                })
                .map(this::ratingItemFromUserData)
                .collect(Collectors.toList());
    }

    public Boolean isActive() {
        return isActive(getCurrentUser());
    }

    public Boolean isActive(UserData user) {
        switch (stageService.getCurrentStage().getStage()) {
            case DEVELOP:
                return true;
            case TEAMS_JOINING:
                return user.getPermissions().contains(UserPermission.TEAM_JOIN);
            default:
                return user.getPermissions().contains(UserPermission.TASKS_DO);
        }
    }

    // TODO: Move it to base service
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private UserData getCurrentUser() {
        val userEmail = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return userRepository.findByStudentDataEmail(userEmail).get();
    }

    private UserInfo setUserInfo(UserData user, UserEditableInfo updatedInfo) {
        if (!user.getName().equals(updatedInfo.getUserName())
                && userRepository.findByName(updatedInfo.getUserName()).isPresent()) {
            throw new NameAlreadyExistsException("Пользователь с таким именем уже существует.");
        }
        if (updatedInfo.getUserName() != null) {
            user.setName(updatedInfo.getUserName());
        }
        if (updatedInfo.getMessengerLink() != null) {
            user.setMessenger(updatedInfo.getMessengerLink());
        }
        return userInfoFromData(user);
    }

    private UserInfo userInfoFromData(UserData data) {
        return new UserInfo(
                new FullUserName(
                        data.getName(),
                        data.getStudentData().getEmail()
                ),
                data.getMessenger(),
                data.getStudentData().getFaculty(),
                data.getAvatarId(),
                data.getAchievements().stream()
                        .map(AchievementsData::toInfo)
                        .collect(Collectors.toList()));
    }

    private RatingItem ratingItemFromUserData(UserData userData) {
        return new RatingItem(userData.getName(), userData.getScore());
    }

    public Boolean isAdmin() {
        return getCurrentUser().getDetails().getRole() == UserRole.ADMIN;
    }
}
