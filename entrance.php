<?php

function unistr_to_xnstr($str){
	return preg_replace('/\\\u([a-z0-9]{4})/i', "&#x\\1;", $str); //한글 필터링
}

$con = mysqli_connect("127.0.0.1", "root", "0403", "KPURESUME"); //서버 연결시 필요함!!!

if(mysqli_connect_errno($con)){
	echo "Failed to connet to MySQL: " . mysqli_connect_error(); //연결 오류시, error메세지주기
}

mysqli_set_charset($con, "utf8"); //한글인데 안드에서 깨지니까 필터링 필요함

$select = $_GET['select'];

/*2016부터 2014년도 까지 모든 전형이 필요하니까 모든걸 다 select하기..*/

if($select == 'GENERAL_2016')
	$res = mysqli_query($con, "select * from GENERAL_2016");
else if($select == 'GENERAL_2015')
	$res = mysqli_query($con, "select * from GENERAL_2015");
else if($select == 'GENERAL_2014')
	$res = mysqli_query($con, "select * from GENERAL_2014");
else if($select == 'SUSI_2016')
	$res = mysqli_query($con, "select * from SUSI_2016");
else if($select == 'SUSI_2015')
	$res = mysqli_query($con, "select * from SUSI_2015");
else if($select == 'SUSI_2014')
	$res = mysqli_query($con, "select * from SUSI_2014");

$result = array();

while($row = mysqli_fetch_array($res)){
	array_push($result, array('year'=>$row[0], 'type'=>$row[1], 'faculty'=>$row[2],
	 'major'=>$row[3], 'recruit'=>$row[4], 'apply'=>$row[5], 'compete'=>$row[6], 'fst_avr'=>$row[7], 'fst_max'=>$row[8],
	  'fst_min'=>$row[9], 'last_avr'=>$row[10], 'last_max'=>$row[11], 'last_min'=>$row[12], 'addition'=>$row[13], 'part'=>$row[14]));
} //년도, 전형, 과, 모집인원, 지원자, 경쟁률, 등등.. 모든 정보를 다 넘겨주기

$json = json_encode(array("result"=>$result)); //안드로이드에서 json으로 받아야되니까 result를 보내주고..
echo $json;

mysqli_close($con);

?>
