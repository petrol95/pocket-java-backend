package ru.geekbrains.pocket.backend.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserChat;
import ru.geekbrains.pocket.backend.domain.pub.UserChatCollection;
import ru.geekbrains.pocket.backend.service.UserChatService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/account")
public class ChatRestController {

    @Autowired
    private UserChatService userChatService;
    @Autowired
    private HttpRequestComponent httpRequestComponent;

    @GetMapping("/chats") //Получить историю чатов
    public ResponseEntity<?> getChats(@RequestParam("offset") Integer offset,
                                      HttpServletRequest request) {
        User user = httpRequestComponent.getUserFromToken(request);
        List<UserChat> userChats;
        if (user != null) {
            userChats = userChatService.getUserChats(user);
            return new ResponseEntity<>(new UserChatCollection(offset, userChats), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}