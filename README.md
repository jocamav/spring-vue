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
	<style> [v-cloak] { display: none; } </style>
</body>
```

## Basics

### Vue instance

Let's create a header section with some title.

```html
<div id="app-todo">
	<section class="todoapp" v-cloak>
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

> This directive will remain on the element until the associated Vue instance finishes compilation. Combined with CSS rules such as [v-cloak] { display: none }, this directive can be used to hide un-compiled mustache bindings until the Vue instance is ready.

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

To see how the binding of properties works, let's add a footer with some links.

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

### Adding a form

We can add an input under the header

```html
<input class="new-todo" autofocus autocomplete="off" placeholder="What needs to be completed?" v-model="newTodo">
```

We need to link the model and the event to a property in Vue.

```javascript
newTodo: ''
```

### List rendering

Add some mock data in JS,

```javascript
var MOCK_TODOS = [
	{"id":1,"title":"Learn JavaScript","completed":false},
	{"id":2,"title":"Learn Vue","completed":true},
	{"id":3,"title":"Build something awesome","completed":false}
];

···

data : {
	···
	todos: MOCK_TODOS
}
```

And include the list in HTML.

```html
<section class="main" v-show="todos.length">
	<input id="toggle-all" class="toggle-all" type="checkbox">
	<label for="toggle-all">Mark all as complete</label>
	<ul class="todo-list">
		<li class="todo" v-for="todo in todos" :key="todo.id" :class="{completed: todo.completed}">
			<div class="view">
				<input class="toggle" type="checkbox" v-model="todo.completed">
				<label>{{todo.title}}</label>
				<button class="destroy"></button>
			</div>
		</li>
	</ul>
</section>
```

>Generally speaking, v-if has higher toggle costs while v-show has higher initial render costs. So prefer v-show if you need to toggle something very often, and prefer v-if if the condition is unlikely to change at runtime

`:class="{completed: todo.completed}"` add a class to the element if the condition is true.

### Binding events

Let's create the method to add a new event:

Add `@keyup.enter="addTodo"` to the input field.


And we will define the function `addTodo' into `methods` property.

```javascript
methods: {
	addTodo: function() {
        var value = this.newTodo && this.newTodo.trim();
        if (!value) {
            return;
        }
        this.todos.push({ id: this.todos.length + 1, title: value, completed: false });
        this.newTodo = '';
    }
}
```

>@keyup is equivalent to v-on. It's known as a Shorthand.

Now we can add the remove functionality. First we create the method in the JS file.

```javascript
removeTodo: function (todo) {
    var index = this.todos.indexOf(todo);
    this.todos.splice(index, 1);
}
```

And now we add `@click="removeTodo(todo)` to the remove button of each todo. 

### Computed properties

First, we are going to create some JS functions outside the Vue instance (just to make easier the filtering of the todos).

```javascript
var filters = {
    all: function (todos) {
        return todos;
    },
    active: function (todos) {
        return todos.filter(function (todo) {
            return !todo.completed;
        });
    },
    completed: function (todos) {
        return todos.filter(function (todo) {
            return todo.completed;
        });
    }
};
```

Now, we are going to some new computed properties.

```
computed: {
    remaining: function () {
        return filters.active(this.todos).length;
    },
    allDone: {
        get: function () {
            return this.remaining === 0;
        },
        set: function (value) {
            this.todos.forEach(function (todo) {
                todo.completed = value;
            });
        }
    }
}
```

> Instead of a computed property, we can define the same function as a method instead. For the end result, the two approaches are indeed exactly the same. However, the difference is that computed properties are cached based on their reactive dependencies. A computed property will only re-evaluate when some of its reactive dependencies have changed. 

So we add `v-model="allDone"` to the in the toggle-all input.

Now, we are going to add a footer below the list of todos with the remaining pending items.

```html
<footer class="footer" v-show="todos.length">
    <span class="todo-count">
        <strong v-text="remaining"></strong> {{pluralize('item', remaining)}} left
    </span>
</footer>
```

### Routing

To use the Routing module, we need to include a new dependency in the HTML.

```html
	<script src="https://rawgit.com/flatiron/director/master/build/director.min.js"></script>
```

Then, we need a new computed property to show the filtered todos. And also a new property 

```javascript
computed: {
    
    ···
    
    filteredTodos: function () {
        return filters[this.visibility](this.todos);
    }
    
    ···
    
}
```

Like we are going to use different list of todos, we need to change the html from `v-for="todo in todos"` to `v-for="todo in filteredTodos"` to use the new computed property.

In the footer section, we are going to add the links to:
* All todos
* Active todos
* Complete todos

```html
<ul class="filters">
    <li><a href="#/all" :class="{selected: visibility == 'all'}">All</a></li>
    <li><a href="#/active" :class="{selected: visibility == 'active'}">Active</a></li>
    <li><a href="#/completed" :class="{selected: visibility == 'completed'}">Completed</a></li>
</ul>

```

Finally, we are going to add the Router configuration.

```javascript
var router = new Router();

['all', 'active', 'completed'].forEach(function (visibility) {
	router.on(visibility, function () {
		app.visibility = visibility;
	});
});

router.configure({
	notfound: function () {
		window.location.hash = '';
		app.visibility = 'all';
	}
});

router.init();
```

> For this example we are using a 3rd-Party Router (Director). For most Single Page Applications, it’s recommended to use the officially-supported vue-router library. 

## References
You can find some extra documentation here:

* [Getting started with Vue.js](https://vuejs.org/v2/guide/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

