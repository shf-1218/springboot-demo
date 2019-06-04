package com.hongfei.springbootshiro.user.service;

import com.github.pagehelper.PageHelper;
import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.common.Constant;
import com.hongfei.springbootshiro.user.mapper.OrganizationMapper;
import com.hongfei.springbootshiro.user.model.Organization;
import com.hongfei.springbootshiro.user.model.dto.OrganizationDto;
import com.hongfei.springbootshiro.user.model.dto.UpdateStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hongfei.shen
 * @date 2019/05/29
 */
@Slf4j
@Service
public class OrganizationService {
    @Resource
    private OrganizationMapper organizationMapper;
    @Resource
    private RoleOrganizationService roleOrganizationService;

    public boolean isExistName(Long id, String name) {
        return this.organizationMapper.isExistName(id, name);
    }

    public Organization selectById(Long id) {
        return this.organizationMapper.selectById(id);
    }


    public PageInfo selectPage(int page, int pageSize, Long parentId) {
        PageHelper.startPage(page, pageSize);
        List<Organization> organizationList = this.organizationMapper.selectAllByParam(parentId);
        return new PageInfo(organizationList);
    }

    public Result insert(OrganizationDto organizationDto) {
        if (this.isExistName(organizationDto.getId(), organizationDto.getName())) {
            return Result.error(ResponseCode.fullname_already_exist.getMessage());
        }
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDto, organization);
        this.organizationMapper.insert(organization);
        return Result.success(organization);
    }

    public Result update(OrganizationDto organizationDto) {
        Organization oldOrganization = this.selectById(organizationDto.getId());
        if (oldOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (oldOrganization.getId() == oldOrganization.getParentId()) {
            return Result.error("上级机构不能选择自己,请选择其他组织机构!");
        }
        if (this.isExistName(organizationDto.getId(), organizationDto.getName())) {
            return Result.error(ResponseCode.fullname_already_exist.getMessage());
        }
        if (Constant.STATUS_DISABLE == organizationDto.getStatus() && !checkStatus(organizationDto.getId())) {
            return Result.error(ResponseCode.data_is_in_use.getMessage());
        }
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDto, organization);
        this.organizationMapper.update(organization);
        return Result.success(organization);
    }

    public Result delete(Long id) {
        Organization oldOrganization = this.selectById(id);
        if (oldOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (Constant.STATUS_DISABLE != oldOrganization.getStatus()) {
            return Result.error("停用状态的才可以删除");
        }
        this.organizationMapper.updateStatus(id, Constant.STATUS_DELETE);
        this.roleOrganizationService.deleteByOrganizationId(id);
        return Result.success(id);
    }

    public Result getById(Long id) {
        Organization oldOrganization = this.selectById(id);
        if (oldOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        return Result.success(oldOrganization);
    }

    public Result updateStatus(UpdateStatus updateStatus) {
        Organization oldOrganization = this.selectById(updateStatus.getId());
        if (oldOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (Constant.STATUS_DISABLE == updateStatus.getStatus() && !checkStatus(updateStatus.getId())) {
            return Result.error(ResponseCode.data_is_in_use.getMessage());
        }
        this.organizationMapper.updateStatus(updateStatus.getId(), updateStatus.getStatus());
        return Result.success();
    }

    private boolean checkStatus(Long id) {
        boolean flag = true;
        if (!this.roleOrganizationService.checkStatusByOrganizationId(id)) {
            flag = false;
        }
        return flag;
    }
}
