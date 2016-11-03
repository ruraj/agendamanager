for i in `seq 2 $1`
do
  for j in `seq 1 $i`
  do
    cat "test1.txt" >> test$i.txt
  done
done

