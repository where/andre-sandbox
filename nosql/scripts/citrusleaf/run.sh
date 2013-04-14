
if [ ! -e user.env ] ; then
  echo "ERROR: Specify properties 'user' and 'hosts' in user.env"
  exit
  fi
. ./user.env
echo "`date`"
echo "user=$user"
echo "hosts=$hosts"

if [ $# -eq 0 ] ; then
  echo "ERROR: Missing command file"
  exit
  fi
file=$1
echo "file=$file"

doproc() {
  for host in $hosts ; do
    echo "----- $host `date`"
    ssh $user@$host < $file | tee log-$host.txt
    done

  echo "file=$file"
  echo "hosts=$hosts"
  echo "`date`"
}

doproc | tee log.txt
