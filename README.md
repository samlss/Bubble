![Head](https://github.com/samlss/Bubble/blob/master/screenshots/head.gif)

 [![Download](https://api.bintray.com/packages/samlss/maven/bubble/images/download.svg?version=1.0.0-snapshot)](https://bintray.com/samlss/maven/bubble/1.0.0-snapshot/link) [![Api reqeust](https://img.shields.io/badge/API-11+-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=11#l11) [![Apache License 2.0](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/samlss/Bubble/blob/master/LICENSE)  

Inspired by Ali Ant Forest.

灵感来源于阿里蚂蚁森林

### Screenshots

#### white bubbles

![white_bubbles](https://github.com/samlss/Bubble/blob/master/screenshots/screenshot1.gif)

<br>

#### yellow bubbles

![yellow_bubbles](https://github.com/samlss/Bubble/blob/master/screenshots/screenshot2.gif)



------
### Dependency

#### Gradle
Add it in your module build.gradle at the end of repositories:
  ```java
  dependencies {
      implementation 'me.samlss:bubble:1.0.0-snapshot'
  }
  ```

#### Maven
```java
<dependency>
  <groupId>me.samlss</groupId>
  <artifactId>bubble</artifactId>
  <version>1.0.0-snapshot</version>
  <type>pom</type>
</dependency>
```

### Sample Usage

```java
Bubble bubble = new Bubble.Builder('activity' or 'viewgroup')
    .setAlpha(0.5f) //Set the alpha of bubble
    .setBubbleColor(Color.YELLOW) //Set the color of bubble
    .setDuration(3000) //Set the duration of animation in millisecond
    .setInterpolator(new LinearInterpolator()) //Set the interpolator of animation
    .setBubbleRadius(30) //Set the radius of bubble
    .build(); //To build a bubble obj

//When you want display when touch screen, you can call the below method like:
@Override
public boolean onTouchEvent(MotionEvent event) {
	if (event.getAction() == MotionEvent.ACTION_DOWN) {
		bubble.shoot((int) event.getX(), (int) event.getY());
	}

	return super.onTouchEvent(event);
}

//other methods
bubble.stop(); //Stop the bubble animations
bubble.destroy(); //Stop the bubble animations & Remove the bubble view so that you can not show bubbles anymore

```


### License

```
Copyright 2019 samlss

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```