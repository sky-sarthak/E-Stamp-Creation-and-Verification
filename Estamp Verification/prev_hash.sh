awk 'NR==1{print $0}' hash.txt | ipfs block get | awk 'NR==1{print $4}'
