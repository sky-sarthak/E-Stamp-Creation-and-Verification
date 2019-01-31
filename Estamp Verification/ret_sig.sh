awk 'NR==1{print $0}' sig_block.txt | ipfs block get | awk 'NR==1{print $0}' > signature.sig
