package com.hongfei.springbootshiro.user.service;

import com.github.pagehelper.PageHelper;
import com.hongfei.springbootshiro.common.model.PageInfo;
import com.hongfei.springbootshiro.common.model.ResponseCode;
import com.hongfei.springbootshiro.common.model.Result;
import com.hongfei.springbootshiro.user.common.Constant;
import com.hongfei.springbootshiro.user.mapper.RoleOrganizationMapper;
import com.hongfei.springbootshiro.user.model.RoleOrganization;
import com.hongfei.springbootshiro.user.model.dto.RoleOrganizationDto;
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
public class RoleOrganizationService {
    @Resource
    private RoleOrganizationMapper roleOrganizationMapper;
    @Resource
    private UserRoleOrganizationService userRoleOrganizationService;

    public boolean isExistName(Long id, Long parentId, String name) {
        return this.roleOrganizationMapper.isExistName(id, name);
    }

    public RoleOrganization selectById(Long id) {
        return this.roleOrganizationMapper.selectById(id);
    }


    public Result insert(RoleOrganizationDto roleOrganizationDto) {
        if (this.isExistName(roleOrganizationDto.getId(), roleOrganizationDto.getParentId(),
                roleOrganizationDto.getName())) {
            return Result.error(ResponseCode.name_already_exist.getMessage());
        }
        RoleOrganization roleOrganization = new RoleOrganization();
        BeanUtils.copyProperties(roleOrganizationDto, roleOrganization);
        this.roleOrganizationMapper.insert(roleOrganization);
        return Result.success(roleOrganization);
    }

    public Result update(RoleOrganizationDto roleOrganizationDto) {
        RoleOrganization oldRoleOrganization = this.selectById(roleOrganizationDto.getId());
        if (oldRoleOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (this.isExistName(roleOrganizationDto.getId(), roleOrganizationDto.getParentId(),
                roleOrganizationDto.getName())) {
            return Result.error(ResponseCode.name_already_exist.getMessage());
        }
        if (Constant.STATUS_DISABLE == roleOrganizationDto.getStatus() && !this.userRoleOrganizationService.checkStatus(roleOrganizationDto.getId())) {
            return Result.error(ResponseCode.data_is_in_use.getMessage());
        }
        RoleOrganization roleOrganization = new RoleOrganization();
        BeanUtils.copyProperties(roleOrganizationDto, roleOrganization);
        this.roleOrganizationMapper.update(roleOrganization);
        return Result.success(roleOrganization);
    }

    public Result delete(Long id) {
        RoleOrganization oldRoleOrganization = this.selectById(id);
        if (oldRoleOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        this.roleOrganizationMapper.updateStatus(id, Constant.STATUS_DELETE);
        this.userRoleOrganizationService.deleteByJobId(id);
        return Result.success(id);
    }

    public PageInfo selectPage(int page, int pageSize, Long organizationId) {
        PageHelper.startPage(page, pageSize);
        List<RoleOrganization> roleOrganizationList = this.roleOrganizationMapper.selectAllParam(organizationId);
        return new PageInfo(roleOrganizationList);
    }

    public Result updateStatus(UpdateStatus updateStatus) {
        RoleOrganization oldRoleOrganization = this.selectById(updateStatus.getId());
        if (oldRoleOrganization != null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        if (Constant.STATUS_DISABLE == updateStatus.getStatus() && !this.userRoleOrganizationService.checkStatus(updateStatus.getId())) {
            return Result.error(ResponseCode.data_is_in_use.getMessage());
        }
        this.roleOrganizationMapper.updateStatus(updateStatus.getId(), updateStatus.getStatus());
        return Result.success();
    }

    public Result getById(Long id) {
        RoleOrganization oldRoleOrganization = this.selectById(id);
        if (oldRoleOrganization == null) {
            return Result.error(ResponseCode.data_not_exist.getMessage());
        }
        return Result.success(oldRoleOrganization);
    }

    public boolean checkStatusByRoleId(Long roleId) {
        boolean flag = false;
        List<RoleOrganization> roleOrganizationList = this.roleOrganizationMapper.selectByRoleId(roleId);
        if (roleOrganizationList.isEmpty()) {
            flag = true;
        }
        return flag;
    }

    public void deleteByRoleId(Long roleId) {
        this.roleOrganizationMapper.deleteByRoleId(roleId);
    }

    public boolean checkStatusByOrganizationId(Long organizationId) {
        boolean flag = false;
        List<RoleOrganization> roleOrganizationList =
                this.roleOrganizationMapper.selectByOrganizationId(organizationId);
        if (roleOrganizationList.isEmpty()) {
            flag = true;
        }
        return flag;
    }

    public void deleteByOrganizationId(Long organizationId) {
        this.roleOrganizationMapper.deleteByOrganizationId(organizationId);
    }

    public List<RoleOrganization> selectByRoleId(Long roleId) {
        List<RoleOrganization> roleOrganizationList = this.roleOrganizationMapper.selectByRoleId(roleId);
        return roleOrganizationList;
    }
}
