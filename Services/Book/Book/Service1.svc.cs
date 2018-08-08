using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.ServiceModel.Web;

namespace Book
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "Service1" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select Service1.svc or Service1.svc.cs at the Solution Explorer and start debugging.
    public class Service1 : IService1
    {
        public List<int> GetBookIds()
        {
            return new Work().GetBookIds();
        }

        public WCF_Book GetBook(string id)
        {
            int n = Int32.Parse(id);
            Book p = new Work().GetBook(n);
            return new WCF_Book(p.BookID, p.Title, p.CategoryID, p.ISBN, p.Stock, p.Price, p.Author);
        }

        public List<Book> FindBooks(string txt)
        {
            return new Work().SearchBooks(txt);
        }

        public void Update(WCF_Book b)
        {
            Console.WriteLine("REACH HERE");
            Book book = new Book
            {
                Author = b.Author,
                BookID = b.BookID,
                CategoryID = b.CategoryID,
                ISBN = b.ISBN,
                Price = b.Price,
                Stock = b.Stock,
                Title = b.Title
            };
            new Work().UpdateBook(book);
        }
    }
}
