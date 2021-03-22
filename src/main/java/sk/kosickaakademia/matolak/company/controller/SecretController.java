package sk.kosickaakademia.matolak.company.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.kosickaakademia.matolak.company.log.Log;
import sk.kosickaakademia.matolak.company.util.Util;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SecretController {
    private final String PASSWORD = "Kosice2021!";
    Map<String, String> map = new HashMap<>();
    Log log = new Log();

    @GetMapping("/secret")
    public ResponseEntity<String> secret(@RequestHeader("token") String header) {
        String token = header.substring(7);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(token)) {
                return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body("{\"secret}\":\"DOD ma byc 9.4.\"");
            }
        }
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String auth) {
        JSONObject object = null;
        try {
            object = (JSONObject) new JSONParser().parse(auth);
            String login = ((String) object.get("login"));
            String password = ((String) object.get("password"));
            System.out.println(login + " " + password);
            if (login == null || password == null) {
                log.error("Missing login or password");
                return ResponseEntity.status(400).body("");
            }
            if (password.equals(PASSWORD)) {
                String token = new Util().generateToken();
                map.put(login, token);
                log.print("User logged");
                JSONObject obj = new JSONObject();
                obj.put("login", login);
                obj.put("token", "Bearer " + token);
                return ResponseEntity.status(200).body(obj.toJSONString());
            } else {
                log.error("Wrong password");
                return ResponseEntity.status(401).body("");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("token") String header) {
        String token = header.substring(7);
        String login = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(token)) {
                login = entry.getKey();
                break;
            }
            if (login != null) {
                map.remove(login);
                log.info("Logout of : " + login);
            } else {
                log.error("Logout failed. User" + login + "does not exist");
            }
        }
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body("Success");
    }
}
