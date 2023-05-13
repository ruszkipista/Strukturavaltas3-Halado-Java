package videos;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityManagerFactory;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserDao repo;
    @Mock
    EntityManagerFactory entityManagerFactory;

    @InjectMocks
    UserService service;

    User user;

    @BeforeEach
    void init() {
        user = new User(42L, "user1", LocalDate.parse("2023-03-05"));
    }

    @Test
    void upload1stVideo_NoUserStatusChange() {
        Mockito.when(repo.countUploadedVideos(anyLong())).thenReturn(0);

        Video video = new Video("video1", user);
        service.uploadVideo(user.getId(), video);

        verify(repo, times(1)).updateUserWithVideo(42L, video);
        verify(repo, never()).updateUserStatus(42L, UserStatus.ADVANCED);
    }

    @Test
    void upload4thVideo_NoUserStatusChange() {
        int alreadyUploadedVideos = 3;
        Video video;
        for(int i=1; i<=alreadyUploadedVideos; i++){
            video = new Video("video"+i, user);
            service.uploadVideo(user.getId(), video);
        }

        Mockito.when(repo.countUploadedVideos(anyLong())).thenReturn(alreadyUploadedVideos);
        video = new Video("video4", user);
        service.uploadVideo(user.getId(), video);

        verify(repo, times(alreadyUploadedVideos+1))
            .updateUserWithVideo(eq(42L), argThat((Video v)->v.getTitle().startsWith("video")));
        verify(repo, never()).updateUserStatus(42L, UserStatus.ADVANCED);
    }

    @Test
    void upload5thVideo_UserStatusChange() {
        int alreadyUploadedVideos = 4;
        Video video;
        for(int i=1; i<=alreadyUploadedVideos; i++){
            video = new Video("video"+i, user);
            service.uploadVideo(user.getId(), video);
        }

        Mockito.when(repo.countUploadedVideos(anyLong())).thenReturn(alreadyUploadedVideos);
        video = new Video("video5", user);
        service.uploadVideo(user.getId(), video);

        verify(repo, times(alreadyUploadedVideos+1))
            .updateUserWithVideo(eq(42L), argThat((Video v)->v.getTitle().startsWith("video")));
        verify(repo, times(1)).updateUserStatus(42L, UserStatus.ADVANCED);
    }

    @Test
    void upload10thVideo_NoUserStatusChange() {
        int alreadyUploadedVideos = 9;
        Video video;
        for(int i=1; i<=alreadyUploadedVideos; i++){
            video = new Video("video"+i, user);
            service.uploadVideo(user.getId(), video);
        }

        Mockito.when(repo.countUploadedVideos(anyLong())).thenReturn(alreadyUploadedVideos);
        video = new Video("video10", user);
        service.uploadVideo(user.getId(), video);

        verify(repo, times(alreadyUploadedVideos+1))
            .updateUserWithVideo(eq(42L), argThat((Video v)->v.getTitle().startsWith("video")));
        verify(repo, never()).updateUserStatus(42L, UserStatus.ADVANCED);
    }

    @Test
    void upload11thVideo_Fails() {
        int alreadyUploadedVideos = 10;
        Video video;
        for(int i=1; i<=alreadyUploadedVideos; i++){
            video = new Video("video"+i, user);
            service.uploadVideo(user.getId(), video);
        }

        Mockito.when(repo.countUploadedVideos(anyLong())).thenReturn(alreadyUploadedVideos);
        final Video lastVideo = new Video("video11", user);

        assertThrows(IllegalStateException.class, ()->service.uploadVideo(user.getId(), lastVideo));

        verify(repo, times(alreadyUploadedVideos))
            .updateUserWithVideo(eq(42L), argThat((Video v)->v.getTitle().startsWith("video")));
        verify(repo, never()).updateUserStatus(42L, UserStatus.ADVANCED);
    }
}
