package com.baofeng.blog.controller.admin;

import com.baofeng.blog.vo.ApiResponse;
import com.baofeng.blog.vo.admin.RouteVO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/api/admin/routes")
@RequiredArgsConstructor
public class RouteController {

    @GetMapping("/get-async-routes")
    public ApiResponse<List<RouteVO.routeResponse>> getAsyncRoutes() {
        List<RouteVO.routeResponse> routeResponses = new ArrayList<>();
    
        // 构造父级路由
        RouteVO.routeResponse response = new RouteVO.routeResponse();
        RouteVO.parent parentRoute = new RouteVO.parent();
        parentRoute.setPath("/user");
        parentRoute.setName("UserManagement");
        
        RouteVO.parentMeta parentMeta = new RouteVO.parentMeta();
        parentMeta.setTitle("用户管理");
        parentMeta.setIcon("ep:user");
        parentMeta.setRank(10);
        parentMeta.setShowlink(true);
        parentRoute.setMeta(parentMeta);
        
        // 构造子路由
        RouteVO.child child1 = new RouteVO.child();
        child1.setPath("/user/index");
        child1.setName("PermissionPage");
        RouteVO.childMeta childMeta1 = new RouteVO.childMeta();
        childMeta1.setTitle("用户列表");
        childMeta1.setRoles(Arrays.asList("admin", "common"));
        child1.setMeta(childMeta1);
        
        RouteVO.child child2 = new RouteVO.child();
        child2.setPath("/permission/button");
        RouteVO.childMeta childMeta2 = new RouteVO.childMeta();
        childMeta2.setTitle("按钮权限");
        childMeta2.setRoles(Arrays.asList("admin", "common"));
        child2.setMeta(childMeta2);
        
        // 构造子路由的子路由
        RouteVO.child buttonRouter = new RouteVO.child();
        buttonRouter.setPath("/permission/button/router");
        buttonRouter.setComponent("permission/button/index");
        buttonRouter.setName("PermissionButtonRouter");
        RouteVO.childMeta buttonRouterMeta = new RouteVO.childMeta();
        buttonRouterMeta.setTitle("路由返回按钮权限");
        buttonRouterMeta.setAuths(Arrays.asList("permission:btn:add", "permission:btn:edit", "permission:btn:delete"));
        buttonRouter.setMeta(buttonRouterMeta);
        
        RouteVO.child buttonLogin = new RouteVO.child();
        buttonLogin.setPath("/permission/button/login");
        buttonLogin.setComponent("permission/button/perms");
        buttonLogin.setName("PermissionButtonLogin");
        RouteVO.childMeta buttonLoginMeta = new RouteVO.childMeta();
        buttonLoginMeta.setTitle("登录接口返回按钮权限");
        buttonLogin.setMeta(buttonLoginMeta);
        
        // 设置按钮权限路由的子路由
        child2.setChildren(Arrays.asList(buttonRouter, buttonLogin));
        
        // 将子路由添加到父路由
        parentRoute.setChildren(Arrays.asList(child1, child2));
        
        // 将父路由包装到 routeResponse 中
        response.setPa(parentRoute);
        
        // 将routeResponse添加到列表
        routeResponses.add(response);
    
        // 返回包装了List的ApiResponse
        return ApiResponse.success(routeResponses);
    }
    
}
