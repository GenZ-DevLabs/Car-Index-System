package com.genzdevlabs.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Customer {
    private String nic;
    private String name;
    private String mobile;
    private String email;
    private String address;
}
