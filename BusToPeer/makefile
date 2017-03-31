clean:remove copy file
	cp conf1.txt conf.txt
	touch Client1/fileVersion.txt
copy:
	cp -R Client1 Client2
	cp -R Client1 Client3
	cp -R Client1 Client4
	cp -R Client1 Client5
	cp -R Client1 Client6
	cp -R Client1 Client7
	cp -R Client1 Client8
	cp -R Client1 Client9
	cp -R Client1 Client10
remove:
	rm -rvf Client1/fileVersion.txt Client1/input_files/* Client2 Client3 Client4 Client5 Client6 Client7 Client8 Client9 Client10
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

