package com.peterson.library.api.domain.repositories;

import java.lang.reflect.Member;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, UUID> {

}
