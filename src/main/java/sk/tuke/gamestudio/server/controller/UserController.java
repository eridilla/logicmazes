package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.UserTable;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.UserService;

@Controller
@RequestMapping("/logicmazes")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    private UserTable loggedUser;

    @RequestMapping("/register")
    public String register(@RequestParam(required = false) String username, @RequestParam(required = false) String password) {
        if (username != null && password != null) {
            userService.addUser(new UserTable(username, password));
            return "redirect:/logicmazes";
        } else {
            return "logicmazesRegister";
        }
    }

    @RequestMapping("/login")
    public String login(@RequestParam(required = false) String username, @RequestParam(required = false) String password) {
        if (username != null && password != null) {
            UserTable temp = userService.getUser(username);

            if (temp == null) {
                return "redirect:/logicmazes/login?failed=1";
            }

            if (temp.getPassword().equals(password)) {
                loggedUser = temp;
                return "redirect:/logicmazes";
            } else {
                return "redirect:/logicmazes/login?failed=2";
            }
        } else if (loggedUser == null) {
            return "logicmazesLogin";
        } else {
            return "redirect:/logicmazes";
        }
    }

    @RequestMapping("/logout")
    public String logout(){
        loggedUser = null;
        return "redirect:/logicmazes";
    }

    public UserTable getLoggedUser(){
        return loggedUser;
    }

    public boolean isLogged(){
        return loggedUser != null;
    }

    public boolean isNotLogged() {
        return loggedUser == null;
    }

    public String getRatingByName() {
        StringBuilder sb = new StringBuilder();

        if (ratingService.getRating("logicmazes", loggedUser.getUsername()) <= 0) {
            sb.append("<i>Your rating:</i>\n");
            sb.append("<i>You haven't submitted a rating yet...</i>\n");
        } else {
            sb.append("<i>Your rating: ");
            sb.append(ratingService.getRating("logicmazes", loggedUser.getUsername()));
            sb.append("/5</i>\n");
        }

        return sb.toString();
    }
}
