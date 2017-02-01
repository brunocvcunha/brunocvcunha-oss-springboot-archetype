(function () {
	var app = angular.module("PostsApp", ["angularjs-datetime-picker"])
		.factory('pageService', pageService)
		.controller('PageController', PageController)
		.controller('SubscriptionController', SubscriptionController)
		.controller('PostsController', PageController)
		.controller('BrowseController', BrowseController)
		;
	
	function pageService($rootScope, $http) {
	  var pageService = {};

	}
	
	
	function PageController($scope, $http, pageService) {
	
		var vm = this;

        $http.get('/rest/subscriptions').success(function (data, status, headers, config) {
            vm.subscriptions = data;
        }).error(function (data, status, headers, config) {
            if (data.message == 'Time is out') {
                console.log("Timeout")
            }
        });
	}
	
	
	function SubscriptionController($scope, $http, pageService) {
		var vm = this;
		
        $http.get('/public/rest/pages').success(function (data, status, headers, config) {
        	vm.pages = data;
        	vm.selectedPage = data[0];
        	vm.changePage(vm.selectedPage.id)
        }).error(function (data, status, headers, config) {
            if (data.message == 'Time is out') {
                console.log("Timeout")
            }
        });
		
        $http.get('/rest/subscriptions').success(function (data, status, headers, config) {
        	vm.subscriptions = data;
        }).error(function (data, status, headers, config) {
            if (data.message == 'Time is out') {
                console.log("Timeout")
            }
        });
		

	    vm.changePage = function (id) {
	        $http.get('/rest/subscriptionModes/' + id).success(function (data, status, headers, config) {
	            vm.modes = data;
	        }).error(function (data, status, headers, config) {
	            if (data.message == 'Time is out') {
	                console.log("Timeout")
	            }
	        });
	    }
	    
	}
	
	function PostsController($scope, $http, $filter) {
	    $scope.getPosts = function () {
	        $http.get('/rest/posts').success(function (data, status, headers, config) {
	            var journals = data;
	            for (var i = 0; i < journals.length; i++) {
	                var journal = journals[i];
	                journal.readLink = "/view/" + journal.id;
	                journal.publishDate = $filter('date')(journal.publishDate);
	            }
	            $scope.postList = journals;
	        }).error(function (data, status, headers, config) {
	            if (data.message == 'Time is out') {
	                $scope.finishByTimeout();
	            }
	        });
	    }
	}
	
	function BrowseController($scope, $http, $filter, $window) {
	    $scope.getPosts = function () {
	        $http.get('/rest/posts/published').success(function (data, status, headers, config) {
	            var posts = data;
	            for (var i = 0; i < posts.length; i++) {
	                var journal = posts[i];
	                journal.publishDate = $filter('date')(journal.publishDate);
	                journal.readLink = "/view/" + journal.id;
	            }
	            $scope.postList = posts;
	        }).error(function (data, status, headers, config) {
	            console.error(status, data, headers);
	        });
	    }
	
	    $scope.delete = function (id) {
	        $http.delete('/rest/posts/unPublish/' + id).success(function (data, status, headers, config) {
	            for (var i = 0; i < $scope.postList.length; i++) {
	                var j = $scope.postList[i];
	                if (j.id == id) {
	                    $scope.postList.splice(i, 1);
	                    break;
	                }
	            }
	        }).error(function (data, status, headers, config) {
	            console.error(status, data, headers);
	        });
	    }
	
	    $scope.view = function (id) {
	        for (var i = 0; i < $scope.postList.length; i++) {
	            var j = $scope.postList[i];
	            if (j.id == id) {
	                $window.location.href = $scope.postList[i].readLink;
	            }
	        }
	    }
	}
	

})();
