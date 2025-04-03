show tables;

create table schedule (
  idx   int  not null auto_increment,
  mid   varchar(20) not null,
  sDate datetime not null,			/* 일정 등록한 날짜 */
  part  varchar(10) not null,		/* 1.모임, 2.업무, 3.학습, 4.여행, 0.기타 */ 
  content text not null,				/* 일정 상세 내역 */
  primary key(idx),
  foreign key(mid) references member(mid)
);
desc schedule;

select * from schedule where date_format(sDate, '%Y-%m') = '2025-04' and mid = 'hkd1234';
select * from schedule where date_format(sDate, '%Y-%m') = '2025-04' and mid = 'hkd1234' order by sDate;
select * from schedule where date_format(sDate, '%Y-%m') = '2025-04' and mid = 'hkd1234' order by sDate, part;
select *, count(part) as partCnt from schedule where date_format(sDate, '%Y-%m') = '2025-04' and mid = 'hkd1234' group by sDate order by sDate, part;
select *, count(part) as partCnt from schedule where date_format(sDate, '%Y-%m') = '2025-04' and mid = 'hkd1234' group by sDate,part order by sDate, part;

insert into schedule values (default,'hkd1234','2025-03-15', '학습', '프로젝트 기획 초안');
insert into schedule values (default,'hkd1234','2025-03-31', '업무', 'A팀 프로젝트 초안 회의');
insert into schedule values (default,'hkd1234','2025-03-31', '모임', 'A팀 단합대회, 17시 충북대 중문 커피숍');
insert into schedule values (default,'hkd1234','2025-04-05', '모임', '식목일 모임, 10시 청주체육관 기념식수');
insert into schedule values (default,'hkd1234','2025-04-09', '학습', 'Springframe AOP 세미너');
insert into schedule values (default,'hkd1234','2025-04-12', '여행', '제주도 1박2일 한라산 등반');
insert into schedule values (default,'hkd1234','2025-04-18', '기타', '봄맞이 대청소');
insert into schedule values (default,'hkd1234','2025-04-22', '학습', '프로젝트 중간점검');
insert into schedule values (default,'hkd1234','2025-04-26', '업무', '제일산업 1차 업무 보고서 제출');
insert into schedule values (default,'hkd1234','2025-04-30', '학습', 'CSS 레이아웃 학습');
insert into schedule values (default,'hkd1234','2025-05-05', '여행', '거제도 해파랑길');
insert into schedule values (default,'kms1234','2025-03-23', '모임', '초등학교 동창회, 성안길 제일카페 18시');
insert into schedule values (default,'kms1234','2025-04-01', '학습', 'JAVA 컬렉션 프레임워크 세미나');
insert into schedule values (default,'kms1234','2025-04-07', '모임', '대학교 동창회 18시 30분 서울 시청앞');
insert into schedule values (default,'kms1234','2025-04-11', '학습', '프론트 학습');
insert into schedule values (default,'kms1234','2025-04-15', '업무', 'Python 기초학습');
insert into schedule values (default,'kms1234','2025-04-20', '기타', '개인 동아리 운용에 대한 점검');
insert into schedule values (default,'kms1234','2025-04-22', '업무', 'J Project 점검');
insert into schedule values (default,'kms1234','2025-04-26', '학습', 'Python 중급학습');
insert into schedule values (default,'kms1234','2025-04-30', '기타', '당근마켓 19시 종합운동장');
insert into schedule values (default,'kms1234','2025-05-04', '여행', '백두산 3박4일 일정');
