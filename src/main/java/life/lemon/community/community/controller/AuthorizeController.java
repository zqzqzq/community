package life.lemon.community.community.controller;

import life.lemon.community.community.dto.AccessTokenDTO;
import life.lemon.community.community.dto.GithubUser;
import life.lemon.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    //将配置文件中的代码放过来
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect}")
    private String redirectUrl;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);


        //已经获取到access_token
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);

        GithubUser user = githubProvider.githubUser(accessToken);
        System.out.println(user.getName());
        System.out.println(user.getBio());
        System.out.println(user.getId());
        return "index";
    }
}
