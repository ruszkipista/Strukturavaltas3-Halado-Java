package videos;

public class UserService {
    public static final int MAX_UPLOADABLE_VIDEOS = 10;
    public static final int MIN_VIDEOS_FOR_ADVANCED = 5;
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void uploadVideo(long userId, Video video){
        Integer uploadedVideos = userDao.countUploadedVideos(userId);
        if ((uploadedVideos+1) == MIN_VIDEOS_FOR_ADVANCED) {
            userDao.updateUserStatus(userId, UserStatus.ADVANCED);
        }
        if (uploadedVideos == MAX_UPLOADABLE_VIDEOS){
            throw new IllegalStateException("Already have maximum number of uploads, video not saved");
        }
        userDao.updateUserWithVideo(userId, video);
    }
}
