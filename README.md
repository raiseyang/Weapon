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

##
https://medium.com/mobile-app-development-publication/upload-to-mavencentral-made-easy-for-android-library-30d2b83af0c7
https://s01.oss.sonatype.org/#stagingProfiles;67e75eb94839e7

## 注意点
使用weapon-ui,需要依赖下述库
```
    api "androidx.annotation:annotation:$annotation_version"
    api "androidx.appcompat:appcompat:$appcompat_version"
    api 'com.google.android.material:material:1.4.0'
```


Execution failed for task ':weapon-base:signReleasePublication'.
> Error while evaluating property 'signatory' of task ':weapon-base:signReleasePublication'
   > Unable to read secret key from file: C:\Users\yangdongsheng\gpg\SecretRingKey.gpg (it may not be a PGP secret key ring)
