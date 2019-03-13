# Learning Vue.js with Spring Boot

## Compile and Run
To run the example locally, execute next command:

`mvn spring-boot:run`

Then, open in a browser:

`http://localhost:8080`

## Starting

You can start with the initial version of the project, checkout the initial tag.

`git checkout initial`

In index.html file you should find:

```html
<!-- development version, includes helpful console warnings -->
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

<div id="app">
    {{ message }}
</div>

<script>
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue!'
        }
    })
</script>
```

## Organize the code

Let's take the css files into `css` folder
* base.css
* index.css

Also, we can move the jss code into a single file called `app.js`.

```javascript
var app = new Vue({
	el : '#app-todo',
	data : {
		message : 'Hello Vue!'
	}
})
```

And modify the `index.html` to include the css files and the js file in the botton.

```
<html>
	<head>
		<meta charset="utf-8">
		<title>Vue.js - TodoMVC - Spring Boot</title>
		<link rel="stylesheet" href="css/base.css">
		<link rel="stylesheet" href="css/index.css">
	</head>
</html>

<body>
	<div id="app-todo">{{ message }}</div>
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
	<script src="js/app.js"></script>
</body>
```



## References
You can find some extra documentation here:

* [Getting started with Vue.js](https://vuejs.org/v2/guide/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

