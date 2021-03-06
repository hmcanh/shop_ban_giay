package edu.niit.luan.shop_ban_giay.services;

import edu.niit.luan.shop_ban_giay.models.User;
import edu.niit.luan.shop_ban_giay.respository.IUserRepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public IUserRepo userRepo;
    @Autowired
    UtilService utilService;
    public boolean add(User user) {
        user.setPassword(utilService.getMd5(user.getPassword()));
        try {
            this.userRepo.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
     public  boolean save(User user){
        try {
            this.userRepo.save( user);
        }catch (Exception e ){
            return  false;
        }
        return  true;
     }
    public PagingResult getPaginate(int page){
        double totalPage = Math.ceil(userRepo.count()/2);// làm tròn lên
        Page<User> listItems = userRepo.findAll(PageRequest.of(page -1 ,2));
        PagingResult pagingResult = new PagingResult();
        pagingResult.setListItems(listItems.getContent());
        pagingResult.setTotalPage(totalPage);
        return  pagingResult;
    }

    @Data
    public static class PagingResult {
        double totalPage;
        List<User> listItems;
    }
    public User getUserById(Long id){
        return userRepo.findById(id).get();
    }
    public  boolean deleteById(Long id ) {
        try {
            userRepo.deleteById(id);
        }catch (Exception e ) {
            return  false;
        }
        return true;
    }
}
