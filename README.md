# CustomView：第一个github依赖项目
依赖配置：
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

implementation 'com.github.YuAndFish:CustomView:v1.1'
