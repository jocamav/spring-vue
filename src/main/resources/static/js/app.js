
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

var app = new Vue({
	el : '#app-todo',
	data : {
		header : 'todo',
		githubUrl: 'https://github.com',
		mvcUrl: 'http://todomvc.com',
		newTodo: '',
		todos: [],
        editedTodo: null,
        visibility: 'all'
	},
    mounted : function() {
        axios
            .get('/api/todo/')
            .then(function(response) {
                this.todos = response.data;
            })
            .catch(function(error) {
                console.log(error)
            });
	},
    computed: {
        filteredTodos: function () {
            return filters[this.visibility](this.todos);
        },
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
    },
	methods: {
        pluralize: function (word, count) {
            return word + (count === 1 ? '' : 's');
        },
		addTodo: function() {
            var value = this.newTodo && this.newTodo.trim();
            if (!value) {
                return;
            }
            this.todos.push({ id: this.todos.length + 1, title: value, completed: false });
            this.newTodo = '';
		},
        removeTodo: function (todo) {
            var index = this.todos.indexOf(todo);
            this.todos.splice(index, 1);
        },
        editTodo: function (todo) {
            this.beforeEditCache = todo.title;
            this.editedTodo = todo;
        },
        doneEdit: function (todo) {
            if (!this.editedTodo) {
                return;
            }
            this.editedTodo = null;
            todo.title = todo.title.trim();
            if (!todo.title) {
                this.removeTodo(todo);
            }
        },
        cancelEdit: function (todo) {
            this.editedTodo = null;
            todo.title = this.beforeEditCache;
        },
        removeCompleted: function () {
            this.todos = filters.active(this.todos);
        }
	},
    directives: {
        'todo-focus': function (el, binding) {
            if (binding.value) {
                el.focus();
            }
        }
    }
});


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

