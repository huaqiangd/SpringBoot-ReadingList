package com.example.chapter03.repo;

import com.example.chapter03.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author donghuaqiang
 * @Title: TOTO
 * @Description: TOTO
 * @date 19-2-28 15:53
 */
public interface UserRepository extends JpaRepository<User, String> {
}
