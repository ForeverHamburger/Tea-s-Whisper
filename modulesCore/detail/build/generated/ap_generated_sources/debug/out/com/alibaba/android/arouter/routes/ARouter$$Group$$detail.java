package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.xuptggg.detail.view.DetailActivity;
import java.lang.Override;
import java.lang.String;
import java.util.Map;

/**
 * DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY AROUTER. */
public class ARouter$$Group$$detail implements IRouteGroup {
  @Override
  public void loadInto(Map<String, RouteMeta> atlas) {
    atlas.put("/detail/DetailActivity", RouteMeta.build(RouteType.ACTIVITY, DetailActivity.class, "/detail/detailactivity", "detail", new java.util.HashMap<String, Integer>(){{put("teaName", 8); }}, -1, -2147483648));
  }
}
