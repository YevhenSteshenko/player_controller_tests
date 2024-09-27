package api.response.models.posts;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter(onMethod = @__(@JsonProperty))
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
public class PostResponse extends BaseResponse {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
