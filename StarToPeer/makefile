clean:remove copy file
copy:
	touch Client1/fileVersion.txt
	cp -R Client1 Client2
	cp -R Client1 Client3
	cp -R Client1 Client4
	cp -R Client1 Client5
	cp -R Client1 Client6
	cp -R Client1 Client7
	cp -R Client1 Client8
	cp -R Client1 Client9
	cp -R Client1 Client10
	echo "127.0.0.1 40001 available" >Client1/conf.txt
	echo "127.0.0.1 40002 available" >>Client1/conf.txt
	echo "127.0.0.1 40003 available" >>Client1/conf.txt
	echo "127.0.0.1 40004 available" >>Client1/conf.txt
	echo "127.0.0.1 40005 available" >>Client1/conf.txt
	echo "127.0.0.1 40006 available" >>Client1/conf.txt
	echo "127.0.0.1 40007 available" >>Client1/conf.txt
	echo "127.0.0.1 40008 available" >>Client1/conf.txt
	echo "127.0.0.1 40009 available" >>Client1/conf.txt
	echo "127.0.0.1 40010 available" >>Client1/conf.txt
	echo "127.0.0.1 40002 available" >Client2/conf.txt
	echo "127.0.0.1 40001 available" >>Client2/conf.txt
	echo "127.0.0.1 40003 available" >Client3/conf.txt
	echo "127.0.0.1 40001 available" >>Client3/conf.txt
	echo "127.0.0.1 40004 available" >Client4/conf.txt
	echo "127.0.0.1 40001 available" >>Client4/conf.txt
	echo "127.0.0.1 40005 available" >Client5/conf.txt
	echo "127.0.0.1 40001 available" >>Client5/conf.txt
	echo "127.0.0.1 40006 available" >Client6/conf.txt
	echo "127.0.0.1 40001 available" >>Client6/conf.txt
	echo "127.0.0.1 40007 available" >Client7/conf.txt
	echo "127.0.0.1 40001 available" >>Client7/conf.txt
	echo "127.0.0.1 40008 available" >Client8/conf.txt
	echo "127.0.0.1 40001 available" >>Client8/conf.txt
	echo "127.0.0.1 40009 available" >Client9/conf.txt
	echo "127.0.0.1 40001 available" >>Client9/conf.txt
	echo "127.0.0.1 40010 available" >Client10/conf.txt
	echo "127.0.0.1 40010 available" >>Client10/conf.txt

file:
	sh sam.sh 1
	mv F* Client1/input_files/
	sh sam.sh 2
	mv F* Client2/input_files/
	sh sam.sh 3
	mv F* Client3/input_files/
	sh sam.sh 4
	mv F* Client4/input_files/
	sh sam.sh 5
	mv F* Client5/input_files/
	sh sam.sh 6
	mv F* Client6/input_files/
	sh sam.sh 7
	mv F* Client7/input_files/
	sh sam.sh 8
	mv F* Client8/input_files/
	sh sam.sh 9
	mv F* Client9/input_files/
	sh sam.sh 10
	mv F* Client10/input_files/

remove: 
	rm -rvf Client1/input_files/* Client1/fileVersion.txt Client2 Client3 Client4 Client5 Client6 Client7 Client8 Client9 Client10
