import React, { useEffect, useState } from 'react';
import { OrderService } from '../services/OrderService';
import type { OrderResponseDTO } from '../types';
import { Link } from 'react-router-dom';

const OrderHistory: React.FC = () => {
  const [orders, setOrders] = useState<OrderResponseDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const userData = localStorage.getItem('user');
  const user = userData ? JSON.parse(userData) : null;
  const userId = user?.id;

  useEffect(() => {
    if (!userId) {
      setLoading(false);
      return;
    }

    const fetchOrders = async () => {
      try {
        const data = await OrderService.getOrdersByUser(userId);
        setOrders(data);
      } catch (err) {
        setError('No se pudieron cargar tus pedidos.');
      } finally {
        setLoading(false);
      }
    };

    fetchOrders();
  }, [userId]);

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    });
  };

  const formatPrice = (amount: number) => {
    return new Intl.NumberFormat('es-ES', { style: 'currency', currency: 'EUR' }).format(amount);
  };

  if (loading) return (
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="spinner-border text-dark" role="status"></div>
    </div>
  );

  if (!userId) return (
    <div className="container py-5 text-center">
      <h2 className="fw-bold">INICIA SESIÓN PARA VER TUS PEDIDOS</h2>
      <Link to="/login" className="btn btn-dark rounded-pill mt-4 px-5 py-2">INICIAR SESIÓN</Link>
    </div>
  );

  return (
    <div className="bg-white min-vh-100">
      <div className="container py-5">
        <h1 className="display-6 fw-bold mb-5 tracking-tighter uppercase">Mis Pedidos</h1>

        {error && <div className="alert alert-danger">{error}</div>}

        {orders.length === 0 ? (
          <div className="text-center py-5 border rounded bg-light">
            <p className="fs-5 text-secondary">Aún no has realizado ningún pedido.</p>
            <Link to="/articles" className="btn btn-dark rounded-pill mt-3 px-4">IR A LA TIENDA</Link>
          </div>
        ) : (
          <div className="d-flex flex-column gap-4">
            {orders.map((order) => (
              <div key={order.id} className="card border-0 shadow-sm overflow-hidden rounded-3">
                <div className="card-header bg-dark text-white p-3 d-flex justify-content-between align-items-center">
                  <div>
                    <span className="small opacity-75 uppercase tracking-wider">Pedido #</span>
                    <span className="fw-bold ms-1">{order.id}</span>
                  </div>
                  <span className="badge bg-light text-dark text-uppercase">{order.status}</span>
                </div>
                <div className="card-body p-4">
                  <div className="row">
                    <div className="col-md-4 mb-3 mb-md-0">
                      <p className="text-secondary small text-uppercase fw-bold mb-1">Fecha</p>
                      <p className="mb-0">{formatDate(order.order_date)}</p>
                    </div>
                    <div className="col-md-4 mb-3 mb-md-0">
                      <p className="text-secondary small text-uppercase fw-bold mb-1">Enviar a</p>
                      <p className="mb-0">{order.shippingAddress.recipientName}</p>
                      <p className="small text-muted">{order.shippingAddress.city}, {order.shippingAddress.countryCode}</p>
                    </div>
                    <div className="col-md-4 text-md-end">
                      <p className="text-secondary small text-uppercase fw-bold mb-1">Total</p>
                      <p className="fs-4 fw-bold mb-0">{formatPrice(order.total_price)}</p>
                    </div>
                  </div>
                  <div className="mt-4 pt-3 border-top d-flex justify-content-end">
                    <button className="btn btn-outline-dark btn-sm rounded-pill px-4">Ver Detalles</button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default OrderHistory;
