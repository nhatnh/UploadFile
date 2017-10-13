<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movie Record Demo</title>

<link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css' />">
<link href="http://netdna.bootstrapcdn.com/font-awesome/2.0/css/font-awesome.css" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/css/app.css' />"></link>

</head>
<body ng-app="myApp" class="ng-cloak">
	<div class="generic-container" ng-controller="uploadCtrl as myCtrl">
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">Movie Record XML File Upload </span>
			</div>
			<div class="formcontainer" ng-init="mySwitch='file'">
				<form name="myForm" class="form-inline">
					<label>
				        <input type="radio" ng-model="mySwitch" value="file">
				        <i>Upload XML file</i>
					</label>
			        <label>
				        <input type="radio" ng-model="mySwitch" value="form">
				        <i>Add New Record</i>
					</label>
			    </form>
			
				<form ng-show="isShown('file')" class="form-inline">
					<div class="form-group">
						<input class="btn btn-success btn-large" type='file'
							file-model='file'>
					</div>
					<button class="btn btn-primary btn-large"
						ng-click="myCtrl.uploadFile(0)">Upload</button>
					<i>(less than 10 MB)</i>
				</form>
				
				<form ng-show="isShown('form')" ng-submit="myCtrl.submit()" name="myForm">
					<input type="hidden" ng-model="myCtrl.record.id" />
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="title">Title</label>
							<div class="col-md-10">
								<input type="text" ng-model="myCtrl.record.title" class="form-control input-sm" 
									placeholder="Enter Title" required ng-minlength="3" name="title" />
								<div class="has-error" ng-show="myForm.$dirty">
									<span ng-show="myForm.title.$error.required">This is a required field</span>
									<span ng-show="myForm.title.$error.minlength">Minimum length required is 3</span>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="director">Director</label>
							<div class="col-md-4">
								<input type="text" ng-model="myCtrl.record.director" class="form-control input-sm"
									placeholder="Enter Director's Name." required name="director" />
								<div class="has-error" ng-show="myForm.$dirty">
									<span ng-show="myForm.director.$error.required">This is a required field</span>
								</div>
							</div>
							<label class="col-md-2 control-lable" for="producer">Producer</label>
							<div class="col-md-4">
								<input type="text" ng-model="myCtrl.record.producer" class="form-control input-sm"
									placeholder="Enter Producer's Name." name="producer" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="checkSum">Check Sum</label>
							<div class="col-md-4">
								<input type="text" ng-model="myCtrl.record.checkSum" class="form-control input-sm"
									placeholder="Enter Check Sum" required name="checkSum" />
								<div class="has-error" ng-show="myForm.$dirty">
									<span ng-show="myForm.checkSum.$error.required">This is a required field</span>
								</div>
							</div>
							<label class="col-md-2 control-lable" for="date">Main Actor</label>
							<div class="col-md-4">
								<input type="text" ng-model="myCtrl.record.mainActor" class="form-control input-sm"
									placeholder="Enter Main Actor's Name" name="date" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="desc">Description</label>
							<div class="col-md-10">
								<input type="text" ng-model="myCtrl.record.desc" class="form-control input-sm"
									placeholder="Enter Description" name="desc" />
							</div>
						</div>
					</div>

					<div class="row">
						<div class="form-actions floatRight">
							<input type="submit" value="Submit"
								class="btn btn-primary btn-large" ng-disabled="myForm.$invalid">
							<button type="button" ng-click="myCtrl.reset()" class="btn btn-warning btn-large" 
								ng-disabled="myForm.$pristine">Clear Form</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<span class="lead">List of Movie Records </span>
			</div>
			<div class="tablecontainer">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>ID.</th>
							<th>Title</th>
							<th>Director</th>
							<th>Producer</th>
							<th>Main Actor</th>
							<th width="280px">Source File</th>
						</tr>
					</thead>
					<tfoot>
						<td colspan="6">
	                        <div class="pagination">
	                            <button	ng-disabled="myCtrl.currentPage == 0" ng-click="myCtrl.currentPage=myCtrl.currentPage-1">« Prev</button>
	                            {{myCtrl.currentPage+1}}/{{myCtrl.numberOfPages()}}
	                            <button ng-disabled="myCtrl.currentPage >= myCtrl.records.length/myCtrl.pageSize - 1" ng-click="myCtrl.currentPage=myCtrl.currentPage+1">Next »</button>
	                        </div>
	                    </td>
					</tfoot>
					<tbody>
						<tr ng-repeat="m in myCtrl.records | startFrom:myCtrl.currentPage*myCtrl.pageSize | limitTo:myCtrl.pageSize">
							<td><span ng-bind="m.id"></span></td>
							<td><span ng-bind="m.title"></span></td>
							<td><span ng-bind="m.director"></span></td>
							<td><span ng-bind="m.producer"></span></td>
							<td><span ng-bind="m.mainActor"></span></td>
							<td>
								<span ng-show="(m.sourceFile == null || m.sourceFile == '')"> 
									<span class="form-inline">
										<div class="form-group">
											<input type="file" class="btn-success" style="width: 170px;" file-model="$parent.file" >
										</div>
										<button class="btn-primary" ng-click="myCtrl.uploadFile(m.id)">Upload</button>
									</span>
								</span> 
								<a href="#">
									<span ng-bind="m.sourceFile" ng-show="(m.sourceFile != null && m.sourceFile != '')">
								</span>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<script src="<c:url value='/js/angular.js' />"></script>
	<script src="<c:url value='/js/app.js' />"></script>
	<script src="<c:url value='/js/service.js' />"></script>
	<script src="<c:url value='/js/controller.js' />"></script>
	<script src="<c:url value='/js/filter.js' />"></script>
</body>
</html>