package tests.json_place_helper.post;

import api.requset.UsersRequest;
import api.response.models.posts.PostResponse;
import api.response.models.posts.UserResponse;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JsonPlaceHelperTest {

    @Test
    public void testGetPostSimple() {
        
        List<PostResponse> collect1 = Arrays.stream(RestAssured
                        .get("https://jsonplaceholder.typicode.com/posts")
                        .as(PostResponse[].class))
                        .collect(Collectors.toList())
//                .as(new TypeRef<List<PostResponse>>() {});
//                .as(PostResponse.class)
        ;
        List<PostResponse> collect2 = Arrays.stream(RestAssured
                        .get("https://jsonplaceholder.typicode.com/posts")
                        .as(PostResponse[].class))
                .collect(Collectors.toList())
//                .as(new TypeRef<List<PostResponse>>() {});
//                .as(PostResponse.class)
                ;
//        collect.get(0).id();
//        System.out.println(collect1.hashCode());
//        System.out.println(collect2.hashCode());
        Assert.assertEquals(collect1, collect2);

        System.out.println(collect1);
        System.out.println(collect2);
    }

    @Test
    public void testGetUsers() {
        List<UserResponse> users = new UsersRequest()
                .get(200)
                .asList("", UserResponse.class);

        System.out.println(users);
    }

    @Test
    public void testGetPost() {
        PostResponse postResponse = RestAssured.get().as(PostResponse.class);
        System.out.println(postResponse);
    }
    
    
}
