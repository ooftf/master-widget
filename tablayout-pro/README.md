## 去掉点击水波纹效果
```
  app:tabRippleColor="@color/transparent"
```

## Activity主题对 tab 不生效，
* 例如：Tab 默认会将小写字符转为大写；
* 一般情况下需要在activity 的 theme 中添加 textAllCaps = false
* 但是在这里是不生效的，这里需要添加到 tabTextAppearance 的 theme 中