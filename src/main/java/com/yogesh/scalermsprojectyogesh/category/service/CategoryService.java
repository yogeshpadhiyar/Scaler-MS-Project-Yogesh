package com.yogesh.scalermsprojectyogesh.category.service;

import com.yogesh.scalermsprojectyogesh.category.model.CategoryBean;
import com.yogesh.scalermsprojectyogesh.category.model.entity.Category;
import com.yogesh.scalermsprojectyogesh.category.repository.CategoryRepository;
import com.yogesh.scalermsprojectyogesh.exception.CategoryModuleException;
import com.yogesh.scalermsprojectyogesh.exception.CustomUsernameNotFoundException;
import com.yogesh.scalermsprojectyogesh.exception.UserFundModuleException;
import com.yogesh.scalermsprojectyogesh.family.model.FamilyMasterBean;
import com.yogesh.scalermsprojectyogesh.family.model.UserFundBean;
import com.yogesh.scalermsprojectyogesh.family.model.entity.FamilyMaster;
import com.yogesh.scalermsprojectyogesh.family.repository.FamilyMasterRepository;
import com.yogesh.scalermsprojectyogesh.family.repository.UserFundRepository;
import com.yogesh.scalermsprojectyogesh.service.CrudService;
import com.yogesh.scalermsprojectyogesh.transaction.model.entity.TransactionType;
import com.yogesh.scalermsprojectyogesh.user.model.entity.UserMaster;
import com.yogesh.scalermsprojectyogesh.user.repository.UserMasterRepository;
import com.yogesh.scalermsprojectyogesh.utility.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService implements CrudService<CategoryBean> {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserMasterRepository userMasterRepository;
    @Autowired
    private UserFundRepository userFundRepository;
    @Autowired
    private FamilyMasterRepository familyMasterRepository;

    //TODO: Introduce transaction on each category creation, fund assign and user fund update, delete category and add back category
    @Override
    public CategoryBean create(CategoryBean categoryBean) throws Exception {
        if (categoryBean.getFamilyId() == null) {
            setFamilyIdFromUser(categoryBean);
        }
        if (categoryBean.getTotalFund() != null && categoryBean.getTransactionType().equals(TransactionType.CREDIT)) {
            categoryBean.setAvailableFund(categoryBean.getTotalFund());
            updateUserAvailableFund(categoryBean);
        }
        categoryBean.setActive(true);
        CategoryBean outputBean = categoryRepository.save(categoryBean.createEntityBean()).createResponseBean();
        return outputBean;
    }

    public void setFamilyIdFromUser(CategoryBean categoryBean) throws CustomUsernameNotFoundException {
        UserMaster userMaster = userMasterRepository.findById(categoryBean.getUserId())
                .orElseThrow(() -> new CustomUsernameNotFoundException(AppConstant.USERID_NOT_FOUNT + categoryBean.getUserId()));
        categoryBean.setFamilyId(userMaster.getFamily().getId());
    }

    public void updateUserAvailableFund(CategoryBean categoryBean) throws UserFundModuleException, CategoryModuleException {
        UserFundBean userFundBean = userFundRepository.findByUserId(categoryBean.getUserId())
                .orElseThrow(() -> new UserFundModuleException(AppConstant.USER_FUND_NOT_ALLOT + categoryBean.getUserId()))
                .createResponseBean();
        if (categoryBean.getTransactionType().equals(TransactionType.DEBIT) ){
            if ( userFundBean.getAvailableAmount().compareTo(categoryBean.getAvailableFund()) > 0) {
                userFundBean.setAvailableAmount(userFundBean.getAvailableAmount().subtract(categoryBean.getAvailableFund()));
            } else {
                throw new CategoryModuleException(AppConstant.CATEGORY_TOTALFUND_GREATERTHAN_AVAILABLEFUND);
            }
        }else if(categoryBean.getTransactionType().equals(TransactionType.CREDIT)){
            userFundBean.setTotalFundAmount(userFundBean.getTotalFundAmount().add(categoryBean.getTotalFund()));
            userFundBean.setAvailableAmount(userFundBean.getAvailableAmount().add(categoryBean.getTotalFund()));
        }
        userFundRepository.updateAvailableAmountByUserId(categoryBean.getUserId(), userFundBean.getAvailableAmount());
    }

    @Override
    public CategoryBean readById(Long id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(()-> new CategoryModuleException(AppConstant.CATEGORY_ID_NOT_FOUNT+id)).createResponseBean();
    }

    @Override
    public CategoryBean update(CategoryBean categoryBean) throws Exception {
        Category category = categoryRepository.findById(categoryBean.getCategoryId()).orElseThrow(()-> new CategoryModuleException(AppConstant.CATEGORY_ID_NOT_FOUNT+categoryBean.getCategoryId()));

        if(category.getTotalFund()==null && categoryBean.getTransactionType().equals(TransactionType.CREDIT)) {
            category.setTotalFund(categoryBean.getTotalFund());
            category.setAvailableFund(categoryBean.getTotalFund());
        }else if (categoryBean.getTransactionType().equals(TransactionType.CREDIT)){
            category.setTotalFund(category.getTotalFund().add(categoryBean.getTotalFund()));
            category.setAvailableFund(category.getAvailableFund().add(categoryBean.getTotalFund()));
        }else if(category.getTotalFund()!=null && categoryBean.getTransactionType().equals(TransactionType.DEBIT)){
            if(category.getTotalFund().compareTo(categoryBean.getAvailableFund())>0){
                category.setAvailableFund(category.getAvailableFund().subtract(categoryBean.getAvailableFund()));
            }else{
                throw new CategoryModuleException(AppConstant.CATEGORY_TOTALFUND_GREATERTHAN_AVAILABLEFUND);
            }
        }

        updateUserAvailableFund(categoryBean);
        return categoryRepository.save(category).createResponseBean();
    }

    @Override
    public String deleteById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new CategoryModuleException(AppConstant.CATEGORY_ID_NOT_FOUNT+id));
        UserFundBean userFundBean = userFundRepository.findByUserId(category.getUserId()).orElseThrow(()-> new UserFundModuleException(AppConstant.USER_FUND_NOT_ALLOT+category.getUserId())).createResponseBean();
        userFundBean.setAvailableAmount(userFundBean.getAvailableAmount().add(category.getAvailableFund()));
        userFundRepository.updateAvailableAmountByUserId(category.getUserId(), userFundBean.getAvailableAmount());
        category.setActive(false);
        categoryRepository.save(category);
        return AppConstant.CATEGORY_DELETED_SUCCESSFULLY;
    }

    public List<CategoryBean> readByUserId(Long userId) throws Exception {
        List<CategoryBean> categoryBeanList =  categoryRepository.findByUserIdAndActive(userId,true).stream().map(Category::createResponseBean).toList();
        if(categoryBeanList.isEmpty()){
            throw new CategoryModuleException(AppConstant.CATEGORY_USER_NOT_FOUND_ANY_CATEGORY);
        }
        return categoryBeanList;
    }

    public Map<String,List<CategoryBean>> readByFamilyId(Long familyId) throws Exception{
        List<CategoryBean> familyCategotyList =  categoryRepository.findByFamilyIdAndActive(familyId,true).stream().map(Category::createResponseBean).toList();
        FamilyMaster familyMaster;
        if(familyCategotyList.isEmpty()){
            throw new CategoryModuleException(AppConstant.CATEGORY_FAMILY_NOT_FOUND_ANY_CATEGORY);
        }else{
            familyMaster = familyMasterRepository.findById(familyId).orElseThrow(()-> new CategoryModuleException(AppConstant.FAMILY_ID_NOT_FOUNT+familyId));
        }
        Map<Long, List<CategoryBean>> userWiseCategoryList = familyCategotyList.stream().collect(Collectors.groupingBy(CategoryBean::getUserId));
        Map<String, List<CategoryBean>> userNameWiseCategoryList = userWiseCategoryList.entrySet().stream().collect(Collectors.toMap(entry->{
            try {
                return familyMaster.getUsers().stream().filter(userMaster -> userMaster.getId().equals(entry.getKey())).findFirst().orElseThrow(()-> new CustomUsernameNotFoundException(AppConstant.USERID_NOT_FOUNT+entry.getKey())).getUsername();
            } catch (CustomUsernameNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }, Map.Entry::getValue));
        return userNameWiseCategoryList;
    }

    public CategoryBean addBackCategory(Long id) throws Exception{
        Category category = categoryRepository.findById(id).orElseThrow(()-> new CategoryModuleException(AppConstant.CATEGORY_ID_NOT_FOUNT+id));
        UserFundBean userFundBean = userFundRepository.findByUserId(category.getUserId()).orElseThrow(()-> new UserFundModuleException(AppConstant.USER_FUND_NOT_ALLOT+category.getUserId())).createResponseBean();
        userFundBean.setAvailableAmount(userFundBean.getAvailableAmount().subtract(category.getAvailableFund()));
        userFundRepository.updateAvailableAmountByUserId(category.getUserId(), userFundBean.getAvailableAmount());
        category.setActive(true);
        return categoryRepository.save(category).createResponseBean();
    }
}
