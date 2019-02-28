/**
 * @Title: TOTO
 * @Description: TOTO
 * @author donghuaqiang
 * @date 19-2-26 15:40
 */

package com.example.chapter03.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "readinglist_role")
public class role {
    @Id
    private Integer roleId;
    private String roleName;
    private String auth;
}
