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

```html
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

## Basics

### Vue instance

Let's create a header section with some title.

```html
	<div id="app-todo">
		<section class="todoapp">
			<header class="header">
				<h1>{{header}}</h1>
			</header>
		</section>
	</div>
```

```
var app = new Vue({
	el : '#app-todop',
	data : {
		header : 'todo'
	}
})
```

#### Instance Lifecycle Hooks

For example, the created hook can be used to run code after an instance is created:

```javascript
	created : function() {
		// `this` points to the vm instance
		console.log('The title: ' + this.header)
	}
```

#### Interpolations

Text

```html
<h1>{{header}}</h1>
```

Conditional

We can use the directive v-if

```html
<p v-if="seen">You have something</p>
```

The property should be visible

```
seen: true
```

Bind for properties

We can add a footer

```html
	<footer class="info">
		<p>Double-click to edit a todo</p>
		<p>Find code in <a v-bind:href="githubUrl">Github</a></p>
		<p>Original project <a v-bind:href="mvcUrl">TodoMVC</a></p>
	</footer>
```

And the links in the js file.

```javascript
	githubUrl: 'https://github.com',
	mvcUrl: 'http://todomvc.com'
```

## Adding a form

We can add an input under the header

```html
<input class="new-todo" autofocus autocomplete="off" placeholder="What needs to be done?" v-model="newTodo" @keyup.enter="addTodo">
```

>@keyup is equivalent to v-on. It's known as a Shorthand.

We need to link the model and the event to a property in Vue and a method.

```javascript
newTodo: ''
```

And we will define the function into `methods` property.

```javascript
	methods: {
		addTodo: function() {
			console.log("Adding a Todo");
			this.newTodo = '';
		}
	}
```


## References
You can find some extra documentation here:

* [Getting started with Vue.js](https://vuejs.org/v2/guide/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

