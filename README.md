## 发布到jcenter上
1. 修改库中的代码
2. 修改库的统一版本号weapon_version
3. 执行gradle任务：jcenterPublish

## 发布到私服nexus上
1. 修改库中的代码
2. 修改库的统一版本号weapon_version
3. 执行gradle任务：publish
IDEA侧边栏的Gradle -> Weapon -> weapon-ui -> Tasks -> publishing -> publish
IDEA侧边栏的Gradle -> Weapon -> weapon-ui -> Tasks -> publishing -> publishToMavenLocal

## 注意点
使用weapon-ui,需要依赖下述库
```
    api "androidx.annotation:annotation:$annotation_version"
    api "androidx.appcompat:appcompat:$appcompat_version"
    api 'com.google.android.material:material:1.4.0'
```