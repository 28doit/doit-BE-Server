package com.example.photologger.photo.mapper;

import com.example.photologger.photo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccountsMapper {

    public void join(User user);
}

