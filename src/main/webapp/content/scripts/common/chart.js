var dom = document.getElementById("private-chart");
var dom2 = document.getElementById("common-chart");
var myChart = echarts.init(dom);
var myChart2 = echarts.init(dom2);
var app = {};
option = null;
option2 = null;
var dataAxis = ['A', 'B', 'C', 'D', 'E', 'F'];
var data = seatCustomer;//[100, 100, 110, 100, 100, 100, 100];
var data2 = commonCustomer;//[200, 200, 210, 200, 200, 200, 200];
var yMax = 0;
var dataShadow = [];
$(function(){
	loadDtaCom();
	reloadDataCom();
	loadDtaSeat();
	reloadDataSeat();
})

function loadDtaCom(){
	yMax = comMax;
	dataShadow = [];
	for (var i = 0; i < data2.length; i++) {
		dataShadow.push(yMax);
	}
	data2 = commonCustomer;
	option2 = {
		    xAxis: {
		        data: dataAxis,
		        axisLabel: {
		            inside: true,
		            textStyle: {
		                color: '#fff'
		            }
		        },
		        axisTick: {
		            show: false
		        },
		        axisLine: {
		            show: false
		        },
		        z: 10
		    },
		    tooltip: {},
		    yAxis: {
		        axisLine: {
		            show: false
		        },
		        axisTick: {
		            show: false
		        },
		        axisLabel: {
		            textStyle: {
		                color: '#999'
		            }
		        }
		    },
		    dataZoom: [
		        {
		            type: 'inside'
		        }
		    ],
		    series: [
		        { // For shadow
		            type: 'bar',
		            itemStyle: {
		                normal: {color: 'rgba(0,0,0,0.05)'}
		            },
		            barGap:'-100%',
		            barCategoryGap:'40%',
		            data: dataShadow,
		            animation: false
		        },
		        {
		            type: 'bar',
		            itemStyle: {
		                normal: {
		                    color: new echarts.graphic.LinearGradient(
		                        0, 0, 0, 1,
		                        [
		                            {offset: 0, color: '#10c55b'},
		                            {offset: 0.5, color: '#10c55b'},
		                            {offset: 1, color: '#10c55b'}
		                        ]
		                    )
		                },
		                emphasis: {
		                    color: new echarts.graphic.LinearGradient(
		                        0, 0, 0, 1,
		                        [
		                            {offset: 0, color: '#35dd7b'},
		                            {offset: 0.7, color: '#35dd7b'},
		                            {offset: 1, color: '#35dd7b'}
		                        ]
		                    )
		                }
		            },
		            data: data2
		        }
		    ]
		};
}

function loadDtaSeat(){
	yMax = seatMax;
	dataShadow = [];
	for (var i = 0; i < data.length; i++) {
		dataShadow.push(yMax);
	}
	data = seatCustomer;
	option = {
	    xAxis: {
	        data: dataAxis,
	        axisLabel: {
	            inside: true,
	            textStyle: {
	                color: '#fff'
	            }
	        },
	        axisTick: {
	            show: false
	        },
	        axisLine: {
	            show: false
	        },
	        z: 10
	    },
	    tooltip: {trigger: 'item', 
            formatter: function(data) 
            	{
            	 debugger
            		return data.name + '：'+data.value; //将小数转化为百分数显示
            	}
	        },
	    yAxis: {
	        axisLine: {
	            show: false
	        },
	        axisTick: {
	            show: false
	        },
	        axisLabel: {
	            textStyle: {
	                color: '#999'
	            }
	        }
	    },
	    dataZoom: [
	        {
	            type: 'inside'
	        }
	    ],
	    series: [
	        { // For shadow
	            type: 'bar',
	            itemStyle: {
	                normal: {color: 'rgba(0,0,0,0.05)'}
	            },
	            barGap:'-100%',
	            barCategoryGap:'40%',
	            data: dataShadow,
	            animation: false
	        },
	        {
	            type: 'bar',
	            itemStyle: {
	                normal: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#10c55b'},
	                            {offset: 0.5, color: '#10c55b'},
	                            {offset: 1, color: '#10c55b'}
	                        ]
	                    )
	                },
	                emphasis: {
	                    color: new echarts.graphic.LinearGradient(
	                        0, 0, 0, 1,
	                        [
	                            {offset: 0, color: '#35dd7b'},
	                            {offset: 0.7, color: '#35dd7b'},
	                            {offset: 1, color: '#35dd7b'}
	                        ]
	                    )
	                }
	            },
	            data: data
	        }
	    ]
	};
}

// Enable data zoom when user click bar.
var zoomSize = 6;
myChart.on('click', function (params) {
    console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
    myChart.dispatchAction({
        type: 'dataZoom',
        startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
        endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
    });
});;
myChart2.on('click', function (params) {
    console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
    myChart2.dispatchAction({
        type: 'dataZoom',
        startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
        endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
    });
});;

function reloadDataSeat(){
	if (option && typeof option === "object") {
	    myChart.setOption(option, true);
	}
}
function reloadDataCom(){
	if (option2 && typeof option2 === "object") {
	    myChart2.setOption(option2, true);
	}
}
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
if (option2 && typeof option2 === "object") {
    myChart2.setOption(option2, true);
}