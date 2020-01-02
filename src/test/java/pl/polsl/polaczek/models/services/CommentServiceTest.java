package pl.polsl.polaczek.models.services;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.polsl.polaczek.models.dao.CommentRepository;
import pl.polsl.polaczek.models.dao.UserRepository;
import pl.polsl.polaczek.models.dto.CommentDto;
import pl.polsl.polaczek.models.entities.Comment;
import pl.polsl.polaczek.models.entities.URole;
import pl.polsl.polaczek.models.entities.User;
import pl.polsl.polaczek.models.exceptions.BadRequestException;
import pl.polsl.polaczek.models.exceptions.EntityDoesNotExistException;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CommentRepository commentRepository;


    private User userRating = new User("UsernameRating", "Password", URole.MODEL);

    private User userRated = new User("UsernameRated", "Password", URole.PHOTOGRAPHER);

    private Comment comment= new Comment(userRating, userRated, 5);

    private CommentDto commentDto = new CommentDto();

    private static final Long NOT_EXISTING_COMMENT_ID = 10L;
    private static final String NOT_EXISTING_USERRATING_USERNAME = "RATING";
    private static final String NOT_EXISTING_USERRATED_USERNAME = "RATED";

    @Before
    public void setUpMocks(){
        initMocks(this);
        comment.setId(1L);

        given(userRepository.findById(userRated.getUsername())).willReturn(Optional.of(userRated));
        given(userRepository.findById(userRating.getUsername())).willReturn(Optional.of(userRating));
        given(commentRepository.findById(comment.getId())).willReturn(Optional.of(comment));

        given(userRepository.findById(NOT_EXISTING_USERRATING_USERNAME)).willReturn(Optional.empty());
        given(userRepository.findById(NOT_EXISTING_USERRATED_USERNAME)).willReturn(Optional.empty());
        given(commentRepository.findById(NOT_EXISTING_COMMENT_ID)).willReturn(Optional.empty());

        given(commentRepository.save(comment)).willReturn(comment);

        commentDto.setRatingUserUsername(userRating.getUsername());
        commentDto.setRatedUserUsername(userRated.getUsername());
        commentDto.setRating(comment.getRating());
    }

    @Test
    public void shouldAddComment(){
        //given
        comment.setId(null);

        //when
        Comment created = commentService.add(commentDto);

        //then
        assertThat(created).isEqualTo(comment);
        verify(commentRepository).save(comment);
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAddCommentIfRatedUserDoesNotExist() {
        //given
        commentDto.setRatedUserUsername(NOT_EXISTING_USERRATED_USERNAME);

        //when
        commentService.add(commentDto);

        //then
        //expect exception
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotAddCommentIfRatingUserDoesNotExist() {
        //given
        commentDto.setRatingUserUsername(NOT_EXISTING_USERRATING_USERNAME);

        //when
        commentService.add(commentDto);

        //then
        //expect exception
    }

    @Test
    public void shouldDeleteComment() {
        //given
        //when
        commentService.delete(comment.getId());

        //then
        verify(commentRepository).deleteById(comment.getId());
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToDeleteNonExistingComment() {
        //given
        //when
        commentService.delete(NOT_EXISTING_COMMENT_ID);

        //then
        //expect exception
    }

    @Test
    public void shouldGetComment() {
        //given
        //when
        Comment actual = commentService.get(comment.getId());
        //then
        assertThat(actual).isEqualTo(comment);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetNonExistingComment() {
        //given
        //when
        commentService.get(NOT_EXISTING_COMMENT_ID);
        //then
        //expect exception
    }

    @Test
    public void shouldGetAllComments() {
        //given
        List<Comment> comments = Lists.newArrayList(
                new Comment(userRating, userRated, 1),
                new Comment(userRating, userRated, 2),
                new Comment(userRating, userRated, 3)
        );
        given(commentRepository.findAll()).willReturn(comments);

        //when
        List<Comment> actual = commentService.getAll();

        //then
        assertThat(actual).containsExactlyElementsOf(comments);
    }

    @Test
    public void shouldGetNoComments() {
        //given
        given(commentRepository.findAll()).willReturn(Lists.newArrayList());
        //when
        List<Comment> actual = commentService.getAll();
        //then
        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldGetCommentsByUsers() {
        //given
        List<Comment> comments = Lists.newArrayList(
                new Comment(userRating, userRated, 1),
                new Comment(userRating, userRated, 2),
                new Comment(userRating, userRated, 3)
        );

        given(commentRepository.findAllByRatingUserUsernameAndRatedUserUsername(userRating.getUsername(),
                userRated.getUsername())).willReturn(comments);
        //when
        List<Comment> actual = commentService.get(userRating.getUsername(), userRated.getUsername());
        //then
        assertThat(actual).containsExactlyElementsOf(comments);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetCommentByNonExistingRatingUser() {
        //given
        //when
        commentService.get(NOT_EXISTING_USERRATING_USERNAME, userRated.getUsername());
        //then
        //expect exception
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetCommentByNonExistingRatedUser() {
        //given
        //when
        commentService.get(userRating.getUsername(), NOT_EXISTING_USERRATED_USERNAME);
        //then
        //expect exception
    }

    @Test
    public void shouldGetCommentsByRatingUser() {
        //given
        List<Comment> comments = Lists.newArrayList(
                new Comment(userRating, userRated, 1),
                new Comment(userRating, userRated, 2),
                new Comment(userRating, userRated, 3)
        );

        given(commentRepository.findAllByRatingUser_Username(userRating.getUsername())).willReturn(comments);
        //when
        List<Comment> actual = commentService.getCommentsByRatingUser(userRating.getUsername());
        //then
        assertThat(actual).containsExactlyElementsOf(comments);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetCommentsByNonExistingRatingUser() {
        //given
        //when
        commentService.getCommentsByRatingUser(NOT_EXISTING_USERRATING_USERNAME);
        //then
        //expect exception
    }

    @Test
    public void shouldGetCommentsByRatedUser() {
        //given
        List<Comment> comments = Lists.newArrayList(
                new Comment(userRating, userRated, 1),
                new Comment(userRating, userRated, 2),
                new Comment(userRating, userRated, 3)
        );

        given(commentRepository.findAllByRatedUser_Username(userRated.getUsername())).willReturn(comments);
        //when
        List<Comment> actual = commentService.getCommentsByRatedUser(userRated.getUsername());
        //then
        assertThat(actual).containsExactlyElementsOf(comments);
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void shouldThrowExceptionWhenTryingToGetCommentsByNonExistingRatedUser() {
        //given
        //when
        commentService.getCommentsByRatingUser(NOT_EXISTING_USERRATED_USERNAME);
        //then
        //expect exception
    }
}