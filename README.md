# Dx-Ball 

A very basic Dx Ball android game for academic course "Embedded Technologies"

# Screenshot

![screenshot_dxball](https://user-images.githubusercontent.com/17286930/30526348-14c3cfb0-9c3b-11e7-8b7a-65dba4b3180e.png)


# Custom Layout
* To change layout for any level, open ```Settings.java``` file and change this 3D array.
* Each 2D array element representes one level layout for obstacles.


```java
/*
 *  '1' for Box and '0' for empty space in layout. 
 *  Add new 2D array element of [5,8] dimension and 
 *  a free brand new level will be added in game. 
 *  No need to adjust code anywhere else for new level.
 */
protected final int[][][] layout = {
		{
			{ 0, 0, 1, 1, 1, 1, 0, 0},
			{ 0, 1, 0, 0, 0, 0, 1, 0},
			{ 1, 0, 0, 1, 1, 0, 0, 1},
			{ 0, 1, 0, 0, 0, 0, 1, 0},
			{ 0, 0, 1, 1, 1, 1, 0, 0}
		},
		{
			{ 1, 1, 1, 0, 0, 1, 1, 1},
			{ 1, 0, 1, 1, 1, 1, 0, 1},
			{ 1, 1, 1, 0, 0, 1, 1, 1},
			{ 1, 0, 1, 1, 1, 1, 0, 1},
			{ 1, 1, 1, 0, 0, 1, 1, 1}
		},
		{ 
			{ 1, 0, 0, 0, 0, 0, 0, 1},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 0, 0, 0, 1, 1, 0, 0, 0},
			{ 0, 0, 0, 0, 0, 0, 0, 0},
			{ 1, 0, 0, 0, 0, 0, 0, 1} 
		}
	};
```


# Apk Download [link](<https://raw.githubusercontent.com/iam-raihan/Dx-Ball/master/Dx%20Ball.apk>) :+1:

