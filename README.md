# Weapon

## 简介

## 使用
```gradle
dependencies {
    def weapon_version = '0.1.0'
    implementation "io.github.raiseyang:weapon-base:$weapon_version"
    implementation "io.github.raiseyang:weapon-ui:$weapon_version"
    implementation "io.github.raiseyang:weapon-base-kt:$weapon_version"
    implementation "io.github.raiseyang:weapon-ui-kt:$weapon_version"
    implementation "io.github.raiseyang:weapon-jetpack-kt:$weapon_version"
}
```


## 发布到maven central上
1. 修改库中的代码
2. 修改库的统一版本号weapon_version
3. 执行gradle任务：publishReleasePublicationToSonatypeRepository
IDEA侧边栏的Gradle -> Weapon -> weapon-ui -> Tasks -> publishing -> publishReleasePublicationToSonatypeRepository
IDEA侧边栏的Gradle -> Weapon -> weapon-ui -> Tasks -> publishing -> publishReleasePublicationToMavenLocal
4. 上nexus repository manager网站上以此操作：
登录 -> Staging Repositories -> 选中具体的Repo -> 点击Close ->(过一会儿) 点击Release ->
(过一会...) 点击左侧的Repositories,可在里面查看
https://s01.oss.sonatype.org/#stagingProfiles;67e75eb94839e7

参考：
https://medium.com/mobile-app-development-publication/upload-to-mavencentral-made-easy-for-android-library-30d2b83af0c7

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
