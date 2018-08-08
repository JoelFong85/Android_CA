using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Book
{
    public class Work
    {
        Model1 model = new Model1();

        public List<Book> GetBooks()
        {
            return model.Books.ToList<Book>();
        }

        public List<int> GetBookIds()
        {
            List<int> list = new List<int>();
            foreach (Book b in GetBooks())
            {
                list.Add(b.BookID);
            }
            return list;
        }

        public Book GetBook(int id)
        {
            return model.Books.Where
                    (p => p.BookID == id).ToList<Book>()[0];
        }

        public List<Book> SearchBooks(string txt)
        {
            return model.Books.Where
                    (p => p.Title.Contains(txt)).ToList<Book>();
        }

        public void UpdateBook(Book book)
        {
            //Book updatedBook = model.Books.Where(b => b.BookID == book.BookID).First();
            //updatedBook.Author = book.Author;
            //updatedBook.Title = book.Title;
            //updatedBook.Price = book.Price;
            //updatedBook.Stock = book.Stock;
            //model.SaveChanges();

            using (Model1 m = new Model1())
            {
                m.Entry(book).State = System.Data.Entity.EntityState.Modified;
                m.SaveChanges();
            }
        }
    }
}