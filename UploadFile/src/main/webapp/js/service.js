'use strict';

App.service('fileUpload', ['$http', function ($http, $q) {
	
	// File uploading service (if id input > 0 will be uploading file to go)
    this.uploadFileToUrl = function(id, file){
       var fd = new FormData();
       fd.append('file', file);
    
       return $http.post('http://localhost:8080/moviedemo/record/upload/'+id, fd, {
          transformRequest: angular.identity,
          headers: {'Content-Type': undefined}
       }).then(
			function(response){
				return response;
			}, 
			function(errResponse){
				console.error('Error while creating record');
				return $q.reject(errResponse);
			}
		);
    }
    
    this.fetchAllMovieRecords = function() {
    	return $http.get('http://localhost:8080/moviedemo/record/')
		.then(
			function(response){
				return response.data;
			}, 
			function(errResponse){
				console.error('Error while fetching records');
				return $q.reject(errResponse);
			}
		);
	}
    
    this.createMovieRecord = function(record){
		return $http.post('http://localhost:8080/moviedemo/record/', record)
		.then(
			function(response){
				return response;
			}, 
			function(errResponse){
				console.error('Error while creating record');
				return $q.reject(errResponse);
			}
		);
    }

 }]);
