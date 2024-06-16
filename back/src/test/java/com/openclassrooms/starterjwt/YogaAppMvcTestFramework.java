package com.openclassrooms.starterjwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class YogaAppMvcTestFramework extends YogaAppTestFramework {

    @Autowired
    protected MockMvc mockMvc;
}
