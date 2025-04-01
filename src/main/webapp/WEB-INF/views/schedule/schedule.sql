show tables;

create table schedule(
		idx int not null auto_increment,
		mid varchar(20) not null,
		sDate datetime not null, /* 일정 등록한 날짜 */
		part varchar(10) not null, /* 1.모임, 2.업무, 3.학습, 4.여행, 0.기타 */
		content text not null, /* 일정 상세 내역 */
		primary key(idx),
		foreign key(mid) references member(mid)
);
desc schedule;


select * from schedule where date_format(sDate, '%Y-%m') = '2025-04' and mid = 'hkd1234';
select * from schedule where date_format(sDate, '%Y-%m') = '2025-04' and mid = 'hkd1234' order by sDate;
select * from schedule where date_format(sDate, '%Y-%m') = '2025-04' and mid = 'hkd1234' order by sDate, part;
select *, count(part) as partCnt from schedule where date_format(sDate, '%Y-%m') = '2025-04' and mid = 'hkd1234' group by sDate order by sDate, part;
select *, count(part) as partCnt from schedule where date_format(sDate, '%Y-%m') = '2025-04' and mid = 'hkd1234' group by sDate,part order by sDate, part;




insert into schedule values (default, 'hkd1234', '2025-03-15', '학습', '프로젝트 기획 초안');
insert into schedule values (default, 'hkd1234', '2025-03-31', '업무', 'A팀 프로젝트 초안 회의');
insert into schedule values (default, 'hkd1234', '2025-04-05', '모임', '식목일 모임, 10시 청주체육관 기념식수');
insert into schedule values (default, 'hkd1234', '2025-04-06', '기타', '식목일 모임의 후기');
insert into schedule values (default, 'green1234', '2025-04-06', '여행', '개인적인 여행지 추천');
insert into schedule values (default, 'atom1234', '2025-04-07', '모임', '월요일 조깅 모임, 6시 충북대');
insert into schedule values (default, 'hkd1234' '2025-04-10', '업무', '프로젝트 2차 회의')
insert into schedule values (default, 'atom1234', '2025-04-11', '모임', '금요일 밤 한 주를 털어내는 산책 21시 충북대');
insert into schedule values (default, 'etom1234', '2025-04-24', '여행', '겨울을 추억하며 다녀왔던 여행지들');
insert into schedule values (default, 'etom1234', '2025-04-26', '기타', '매 달 마지막주 토요일에 찾아오는 고민상담');
insert into schedule values (default, 'green1234','2025-04-30', '여행','4월의 여행기록');
insert into schedule values (default, 'atom1234', '2025-04-29', '학습', '나의 취미 공유');