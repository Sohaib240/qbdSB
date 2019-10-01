package com.hourtimesheet.controller;

import com.hourtimesheet.delegate.QuickbooksDesktopDelegate;
import com.hourtimesheet.model.FileResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hassan on 2/5/17.
 */
@Controller
public class QuickBooksDesktopController {

    private final QuickbooksDesktopDelegate quickbooksDesktopDelegate;

    @Autowired
    public QuickBooksDesktopController(QuickbooksDesktopDelegate quickbooksDesktopDelegate) {
        this.quickbooksDesktopDelegate = quickbooksDesktopDelegate;
    }

    @RequestMapping(value = "/qwcFile/{companyName}", method = RequestMethod.GET)
    @ResponseBody
    public FileResponseModel getFile(@PathVariable("companyName") String companyName, @RequestParam(value = "email", required = true) String email, @RequestHeader(value = "password", required = true) String password) {
        return quickbooksDesktopDelegate.getQwcFile(companyName, email, password);
    }
}