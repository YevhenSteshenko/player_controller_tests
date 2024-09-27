package api.response.models.posts;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/*{
"id": 1,
"name": "Leanne Graham",
"username": "Bret",
"email": "Sincere@april.biz",
"address": {
"street": "Kulas Light",
"suite": "Apt. 556",
"city": "Gwenborough",
"zipcode": "92998-3874",
"geo": {
"lat": "-37.3159",
"lng": "81.1496"
}
},
"phone": "1-770-736-8031 x56442",
"website": "hildegard.org",
"company": {
"name": "Romaguera-Crona",
"catchPhrase": "Multi-layered client-server neural-net",
"bs": "harness real-time e-markets"
}
},*/

@Getter(onMethod = @__(@JsonProperty))
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends BaseResponse {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    @Getter(onMethod = @__(@JsonProperty))
//    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Address extends BaseResponse{
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;
    }

    @Getter(onMethod = @__(@JsonProperty))
//    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Company extends BaseResponse{
        private String name;
        private String catchPhrase;
        private String bs;
    }

//    @Getter
    @Getter(onMethod = @__(@JsonProperty))
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @EqualsAndHashCode(callSuper = true)
    public static class Geo extends BaseResponse{
        private String lat;
        private String lng;
    }
}
