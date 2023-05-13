package user;

public class UserDao_Handler {

    private PersistenceContextHandler handler;

    public UserDao_Handler(PersistenceContextHandler handler) {
        this.handler = handler;
    }

    public void saveUser(User user) {
        handler.doInTransaction(entityManager -> entityManager.persist(user));
    }

    public User getUser(User user) {
        return handler.queryWithTransaction(entityManager -> 
                    entityManager.createQuery("SELECT u FROM User u WHERE u.id = :userId",
                                             User.class)
                .setParameter("userId", user.getId())
                .getSingleResult());
    }

    public User findUser(long userId) {
        return handler.query(entityManager -> entityManager.find(User.class, userId));
    }

    public Video addVideoToUser(Long userId, Video video) {
        return handler.queryWithTransaction(entityManager -> {
                    User user = entityManager.find(User.class, userId);
                    video.setUser(user);
                    entityManager.persist(video);
                    user.getVideos().add(video);
                    video.setUser(user);
                    return video;
               });
    }

    public User findUserByIdWithVideos(Long userId) {
        return handler.query(entityManager -> 
                entityManager.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.videos v WHERE u.id = :userId",
                User.class)
                .setParameter("userId", userId)
                .getSingleResult());
    }
}
