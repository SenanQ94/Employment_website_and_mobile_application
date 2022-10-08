using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WcfService1
{
    public class CreateCard
    {
        public long cardNumber { set; get; }
        public long vehicleNumber { set; get; }
        public long gasolinAmount { set; get; }
    }
}