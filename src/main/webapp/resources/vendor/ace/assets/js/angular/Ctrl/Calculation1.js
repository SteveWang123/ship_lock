/**
 * Created by wangyuannan on 2015/12/4.
 */
angular.module('shiplock', [])
    .controller('calculation_step1', function($scope){

        $scope.total;
        $scope.chuan;

        showMessage = function(msg){
            console.log(msg);
        }


        collectionParams = function(){

            showMessage("total: " + $scope.total);
            showMessage("chuan collection: " +  $scope.chuan);

        }

    });